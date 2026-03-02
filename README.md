# Languages and Automata – TP 5: Static Analysis

This repository contains the **starter code** for **TP 5** of the *Languages and Automata* course at Nantes University.

The goal of this practical session is to introduce **static program analysis** on a small imperative language.
Unlike interpretation, static analysis reasons about programs **without executing them**, in order to detect
potential errors and guaranteed properties.

In this TP, students extend and experiment with an analyser that detects:
- use of variables before initialization,
- unreachable (dead) code after a `interrompre;` instruction.

See the [main organization](https://github.com/LangagesEtAutomates/) for general information on the course
and other practical sessions.

---

## Structure

```
├── LICENSE.txt           # MIT license (see organization-wide license file)
├── README.md             # This file
├── build.xml             # Ant build file
├── lib/                  # External libraries (JUnit, etc.)
├── src/                  # Source files
│   └── lea/              # Main Java package
│       ├── Analyser.java # Static analysis implementation
│       ├── Context.java  # Analysis context (declared, written, accessibility)
│       ├── *.java        # AST, interpreter, parser, lexer, etc.
├── gen/                  # Generated sources (lexer and parser)
├── build/                # Compiled main classes
├── test/                 # JUnit test sources
│   └── lea/              # Tests for static analysis and other phases
├── build-test/           # Compiled test classes
```

Generated directories (`gen/`, `build/`, `build-test/`) are produced automatically and should not be edited manually.

---

## Objectives

This TP focuses on two main aspects of static analysis:

### 1. Detection of uninitialized variables

The analyser tracks:
- declared variables,
- variables that are *certainly initialized* at each program point.

A variable read before being initialized is reported as a static error.

Special attention is paid to **conditionals** and **loops**, where initialization information must be
merged conservatively.

### 2. Detection of dead code after `interrompre;`

The language is extended with an `interrompre;` instruction (similar to `break` in C/Java).

The analyser detects instructions that are **unreachable** because they appear after an unconditional break
in the same control-flow path.

---

## Build and Execution

The project uses **Apache Ant**.

- Compile all sources (including generated code):

```bash
ant compile
```

- Run the full JUnit test suite:

```bash
ant test
```

- Run the interpreter (`lea.Main`):

```bash
ant run
```

- Clean generated and compiled files:

```bash
ant clean
```

The project targets **Java 21**.

---

## Dependencies

All dependencies are provided in the `lib/` directory:

- **JUnit 5** — unit testing
- (Lexer and parser dependencies are already generated for this TP)

No external installation is required beyond a JDK and Ant.

---

## License

All **source code** in this repository is distributed under the **MIT License**.

- The full legal text is available in [`LICENSE.txt`](LICENSE.txt).
- Organization-wide licensing details and attributions are documented in  
  https://github.com/LangagesEtAutomates/.github/blob/main/LICENSE.md

This license applies to all Java sources and test code in this repository.

---

## Contributing

Contributions are welcome, in particular:
- improvements to the static analysis precision,
- additional test cases,
- clarifications or documentation fixes.

Please use pull requests to propose changes.
For significant modifications, consider opening an issue first to discuss the design.
