package lea;

import org.junit.jupiter.api.Test;
import lea.Reporter.Phase;

/**
 * JUnit tests for the Parser class.
 */
public final class Exo1Tests {

	/* =========================
	 * === Question 1b       ===
	 * ========================= */

	@Test
	void variable_initialization() {
		new LeaAsserts("""
				   algorithme
				   variables
				     x;
				   début
				     x <- x + 1;
				   fin
				""").assertHasErrorContaining(Phase.STATIC, "Variable non initialisée");
	}

	@Test
	void variable_declaration() {
		new LeaAsserts("""
				   algorithme
				   variables
				   début
				     x <- 0;
				     x <- x + 1;
				   fin
				""").assertHasErrorContaining(Phase.STATIC, "Variable non déclarée");
	}

	@Test
	void conditional_branch_initialization() {
		new LeaAsserts("""
				   algorithme
				   variables
				     x;
				   début
				     si 1+1=2 alors
				       x <- 0;
				     sinon
				       écrire(x);
				     fin si
				     x <- x+1;
				   fin
				""").assertHasErrorContaining(Phase.STATIC, "Variable non initialisée");
	}

	/* =========================
	 * === Question 1c       ===
	 * ========================= */

	@Test
	void conditional_initialization() {
		new LeaAsserts("""
				    algorithme
				    variables
				      x : entier;
				    début
				      si vrai alors
				        x <- 0;
				      sinon
				        écrire(1);
				      fin si
				      écrire(x);
				    fin
				""").assertHasErrorContaining(Phase.STATIC, "Variable non initialisée");
	}

	@Test
	void initialization_in_conditional() {
		new LeaAsserts("""
				    algorithme
				    variables
				      x : entier;
				    début
				      si vrai alors
				        x <- 0;
				      sinon
				        x <- 1;
				      fin si
				      écrire(x);
				    fin
				""").assertHasNoError();
	}

	@Test
	void conditional_two_variables() {
		new LeaAsserts("""
				    algorithme
				    variables
				      x;
				      y;
				    début
				      si vrai alors
				        x <- 0;
				      sinon
				        y <- 1;
				      fin si
				      écrire(x);
				      écrire(y);
				    fin
				""").assertHasErrorContaining(Phase.STATIC, "Variable non initialisée");
	}


}