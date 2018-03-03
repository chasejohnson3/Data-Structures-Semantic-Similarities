# Data-Structures-Semantic-Similarities
A year-end project for Data Structures that runs algorithms on words in a book to find their "semantic similarity"

The purpose of this project was to utilize data structures to efficiently handle large amounts of data.  In this particular application,
we find the semantic similarity between words by comparing the context in which each word is used using an object called a vector.  A vector
contains a base word and a hashmap of related words.  These 'related' words are words found in the same sentence as the base word and are 
stored in the hashmap with the key being the string of the related word and the value being the number of times a related word shows up in
the same sentence as the base word.  These vectors can be compared with various means of data calculation including euclidean distances to 
determine how similar words are.

The interesting code in this repository is all in src/edu/uiowa/cs/similarity/ where the two most important files are Main.java and Vector.java.
The main function allows a user to pass in command line options such as passing in text documents, choosing the means of similarity 
calculation, among others.  By the end of the project, we were able to successfully complete every form of word comparison calculation and 
received the highest score on this final project in a class of about 300 students.

