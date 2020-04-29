#!/bin/sh

export JAVA_HOME=/usr/bin/java
set -x
cd ..

javac afs/*.java
test $? -eq 0 || exit 1

jar cf cliente1/afs_cliente.jar afs/Venus.class afs/VenusFile*.class afs/VenusCB.class afs/VenusCBImpl*.class afs/Vice.class afs/ViceReader.class afs/ViceWriter.class

jar cf servidor/afs_servidor.jar afs/Vice.class afs/ViceImpl*.class afs/ViceReader.class afs/ViceWriter.class afs/ViceReaderImpl*.class afs/ViceWriterImpl*.class afs/VenusCB.class afs/LockManager*.class
