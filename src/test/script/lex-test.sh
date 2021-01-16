#!/bin/sh
for i in ./src/test/deca/syntax/valid/provided/*.deca
    do
        echo "$i"
        test_lex "$i"
    echo "---------------------------------------------------------------------------------------"
    done
