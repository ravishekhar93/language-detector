# language-detector
A programming language detector, no UI
Team:

Team Members:
Ravi Shekhar Sinha
Sunny Shekhar Sinha

===================Overview===================
This code will Identify the language in which the given computer program is written.
Write a program which accepts another program’s source code through STDIN and prints out the language detected.

LANGUAGE SET
[“c/c++”, “java”, “python”, “ruby”, “perl”, “javascript” ]

ASSUMPTIONS
The source code provided in the input is syntactically correct.

SALIENT FEATURE
The Language set can add a new language without touching the core code, just by adding a file and appending it to /TEST/langSpec/config/languageList.txt + Adding the Keyword List file

==================Running/Installation Instructions=================

Import this project on Eclipse
Insert the sample code in /TEST/langSpec/input/testFile.txt
and run the Program FinalList.java.

===============Bad Test cases=======================================
If a program is written inside Comments or  String or equivalent of another Programming Language's code then it may be ineffective.
Currently heavy emphasis on any language is signified by adding/ reducing 500 from it. Which must be taken care of, as magic numbers
will lead to trouble

===============Future Additons================================
We can use AVL tree /Indexing  for faster search.

The use of 80:20 rule i.e. searching the input using language specific coding patterns (coding style), 
 can reduce time significantly on average cases.

Better algorithm for ignoring comments, strings etc.

=========================Detailed Working Principle============================================
During Initialization (When Constructor is called):
The program recieves the list of keywords for each language, specified in langspec/config/languageList.txt
The List for each language is presenet as a seperate text file in /langspec
A dictionary keeps them.

Now, When a user inputs the Input File Name. The program extracts tokens from it. And compares it with the dictionary. The language which gets highest hits, gets returned as the answer.

In addition, before Dictionary comaprison, an intital attempt is been made to search common patterns. Whose results are returned as weights to respective languages.






