# USAGE

From project root:

```bash
javac -d out $(find src -name "*.java")
java -ea -cp out edu.ccrm.cli.CCRMApp
```

Try:
- Import Data → load `test-data/*.csv`
- Reports → GPA distribution
- Backup & Show Size → creates timestamped folder and recursively computes its size
