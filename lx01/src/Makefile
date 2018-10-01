all: simple simple.s Simple.class

simple: simple.c
	clang -o simple simple.c

simple.s: simple.c
	clang -O0 -S simple.c

Simple.class: simple.scala
	scalac simple.scala

clean:
	rm -f simple simple.s *.class

run-c: simple
	./simple

run-s: Simple.class
	scala Simple

run-j: Simple.class
	java -classpath `brew --prefix`/opt/scala/libexec/lib/scala-library.jar:. Simple
