JFLAGS = -g
JC = javac
JAR = jar
.SUFFIXES: .java .class

CLASSES = \
        hashtagcounter.java\
        MaxFibonacciHeap.java \
        Node.java
classes: $(CLASSES:.java=.class)


.java.class:

	$(JC) $(JFLAGS) $*.java
	$(JAR) cvfe hashtagcounter hashtagcounter.class MaxFibonacciHeap.class Node.class

default: classes

clean:
	$(RM) *.class *.jar
