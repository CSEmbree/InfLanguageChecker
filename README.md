
# Background 

Project for `COMP454-01 Fall 2013, 12/03/2013` at CSUCI. Using the following theorem:

THEOREM 19 from Introduction to Computer Theory by Daniel I. A. Cohen (pg. 215)

Let F be an FA with N states. Then:

```
1. If F accepts an input string w such that
    N <= length(w) < 2N
then F accepts an infinite lanuage.

2. If F accepts infinitly many words, then F accepts some word w such that
    N <= length(w) < 2N
```

Using Part 1 of Theorem 19 above, we can test a graph defining a language by testing all strings of all combinations of the alphabet for this language and see if any word we can create is accepted. By Part 1, if any word is accepted, then this language in INFINITE, else it is FINITE.



TM GRAPH DESCRIPTIONS:
Located: ../InfLanguageChecker/JavaSourceCode/InfiniteLanguageCheck/
	
Description Format in text files is: 
```
	<SOURCE_STATE>-<SOURCE_TYPE>-<DESTINATIONCOST>-...-<DESTINATIONCOST>
```	

`SOURCE`: State that this vertex describes

`SOURCE_TYPE`: The type of State, either `START` (where we begin parsing a word), `FINAL` (terminal state - where a word would be accepted if we ran out of input at this state), or `NEUTRAL` (a transitional state, a word would not be accepted if we stopped here). 

`DESTIANTIONCOST`: numerical number of the state this nodes is connected to and the alphabetical cost associated with traversing this edge to reach that destination state.

	    
Some examples of TM descriptions:
```
	`0-S-2b-1a`
	    State 0 is a START vertecy and has two edges. One edge from State 0 to 2 with a cost 'b' and from State 0 to 1 with cost 'a'.
	`1-N-2b-1a`
	    State 1 is a NEUTRAL (not init or terminal) vertecy and has two edges. One edge from State 1 to 2 with a cost 'b' and from State 1 to 1 with cost 'a' (Goes back to itself).
	`2-F-4a-4b-3a`
	    State 2 is a FINAL (Terminal) vertecy and has three edges. One edge from State 2 to 4 with a cost 'b', from State 2 to 4 with cost 'b', and from State 2 to 3 with a cost 'a' (notice this introduces two edges with the same cost - and therefore is non-deterministic).
```


# RUNNING
	Open project in Eclipse and run it. Main already contains description of the graph for the lagnuage from HW3Q4 (i), (ii), (iii) and (iv), followed by weather these languages are infinite or finite.

