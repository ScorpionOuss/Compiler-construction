#!/bin/sh
for i in ./src/test/deca/context/valid/valid_sans_objet/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done

for i in ./src/test/deca/context/valid/valid_hello_world/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done

for i in ./src/test/deca/context/valid/valid_essentiel/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done

for i in ./src/test/deca/context/valid/valid_complet/*.deca
    do
        echo "$i"
        cat $i
        echo "\n"
        test_context "$i"
    echo "---------------------------------------------------------------------------------------"
    done
