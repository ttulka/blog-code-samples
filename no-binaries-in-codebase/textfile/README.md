# No Binaries in the Codebase

Sample code for the article http://blog.ttulka.com/no-binaries-in-the-codebase-131

---

To install the test resources artifact into the local Maven repository:
```
mvn install:install-file -Dfile=textfile-test-resources.zip \
    -DgroupId=com.ttulka.samples.testdata -DartifactId=textfile-test-resources \
    -Dversion=1.0.0 -Dpackaging=zip -Dclassifier=testdata
```

To run tests:
```
mvn clean test
``` 
