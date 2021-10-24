#!/bin/bash

mvn install:install-file -Dfile=jarlibs/jmf.jar -DgroupId=javax.media -DartifactId=jmf -Dversion=2.1.1e -Dpackaging=jar
mvn install:install-file -Dfile=jarlibs/j3dcore.jar -DgroupId=javax.media -DartifactId=j3dcore -Dversion=2.1.1e -Dpackaging=jar
mvn install:install-file -Dfile=jarlibs/j3dutils.jar -DgroupId=javax.media -DartifactId=j3dutils -Dversion=2.1.1e -Dpackaging=jar
mvn install:install-file -Dfile=jarlibs/vecmath.jar -DgroupId=javax.media -DartifactId=vecmath -Dversion=2.1.1e -Dpackaging=jar
