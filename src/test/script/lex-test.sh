#!/bin/sh
echo "Tests lexicaux valides"
for i in ./src/test/deca/syntax/valid/*.deca
    do
        echo "$i"
        cat $i
        echo "***********Résultat***********"
        test_lex "$i"
    echo "---------------------------------------------------------------------------------------"
    done
