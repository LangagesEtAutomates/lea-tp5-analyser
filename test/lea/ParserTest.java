package lea;

import org.junit.jupiter.api.Test;
import lea.Reporter.Phase;

/**
 * JUnit tests for the Parser class.
 */
public final class ParserTest {

	/* =========================
	 * === PROGRAMMES VALIDES ==
	 * ========================= */

	@Test
	void minimalProgram_emptyBody() {
		new LeaAsserts("""
			algorithme
			début
			fin
			""").assertHasNoError();
	}

	@Test
	void declarations_with_and_without_types() {
		new LeaAsserts("""
			algorithme
			variables
				x;
				y : entier;
				b : booleen;
			début
				x <- 1;
			fin
			""").assertHasNoError();
	}

	@Test
	void expressions_priority_smoke() {
		new LeaAsserts("""
			algorithme
			début
				écrire(non vrai ou 1 + 2 * 3 < 10 et faux);
			fin
			""").assertHasNoError();
	}

	@Test
	void structuredStatements_full_suite() {
		new LeaAsserts("""
			algorithme
			variables
				x; i;
			début
				si x = 0 alors
					écrire(0);
				sinon
					écrire(1);
				fin si

				tant que x < 3 faire
					x <- x + 1;
				fin tant que

				pour i de 1 à 3 faire
					écrire(i);
				fin pour

				pour i de 5 à 1 pas -1 faire
					écrire(i);
				fin pour
			fin
			""").assertHasNoErrorAt(Phase.PARSER);
	}

	/* =========================
	 * === ERREURS STRUCTURELLES
	 * ========================= */

	@Test
	void programStructureError_isReported() {
		new LeaAsserts("""
			algorithme
			variables
				x;
			fin
			""").assertHasErrorContaining(Phase.PARSER, "Erreur dans le programme");
	}

	/* =========================
	 * === catch_expr : MANQUANT
	 * ========================= */

	@Test
	void missingExpression_inIfCondition() {
		new LeaAsserts("""
			algorithme
			début
				si alors écrire(1); fin si
			fin
			""").assertHasErrorContaining(Phase.PARSER, "Expression manquante");
	}

	@Test
	void missingExpression_inForLoop() {
		new LeaAsserts("""
			algorithme
			début
				pour i de 1 à faire écrire(i); fin pour
			fin
			""").assertHasErrorContaining(Phase.PARSER, "Expression manquante");
	}

	/* =========================
	 * === catch_expr : CASSEE
	 * ========================= */

	@Test
	void invalidExpression_inAssignment() {
		new LeaAsserts("""
			algorithme
			début
				x <- 1 + * 2;
			fin
			""").assertHasErrorContaining(Phase.PARSER, "");
	}

	@Test
	void invalidExpression_inIf_triggersConditionalError() {
		new LeaAsserts("""
			algorithme
			début
				si x < alors
					écrire(1);
				fin si
			fin
			""").assertHasErrorContaining(Phase.PARSER, "");
	}

	/* =========================
	 * === RECOVERY & MULTIPLE ERRORS
	 * ========================= */

	@Test
	void recovery_afterBadCommand_continues() {
		new LeaAsserts("""
			algorithme
			début
				écrire(1;
				x <- 2;
				écrire(x);
			fin
			""").assertHasErrorContaining(Phase.PARSER, "");
	}

	@Test
	void multiple_errors_reported() {
		new LeaAsserts("""
			algorithme
			début
				x <- ;
				y <- + 1;
			fin
			""").assertHasErrorContaining(Phase.PARSER, "Erreur dans la commande");
	}
}