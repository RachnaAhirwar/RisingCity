JC = javac
JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	building.java \
	minheap.java \
	RBT.java \
	risingCity.java 
	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
