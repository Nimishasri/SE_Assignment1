Possible schema definition for the program:
 - char    - Boolean arg.
 - char*   - String arg.
 - char#   - Integer arg.
 - char##  - double arg.
 - char[*] - one element of a string array.
Choose any character followed by the corresponding special character type defined above to  assign values to the schema elements.
The current code executes for the following schema definition:
(Defined in ArgsMain.java)
l - Boolean mapped to logging 
p# - Integer for port information
d* - String for Directory information

Users can choose to pass values from the command prompt for the above.
Initialization values:
Bool flag - false
Int = 0
String  ""

Expected input: Comma separated schema element and value pairs preceded by '-'
-<Schema element> <value>, -<

Schema defined in the application code:
l,p#,d*"

Sample input:
-l, -p# 230, -d c:\cncn


The '-' in the input identifies the character next to be a schema element and the value thereafter in the string is assigned to the element
As per the code:
-l - Boolean parameter for logging, default value false. To set the value to true, simply pass the flag. The presence of the flag"-l" asserts the Boolean value to true

-p - Integer parameter specifying the port. Default value is set to be 0. If no parameter passed, the default value is shown.
-d - String parameter, specifying Directory. Default value is set to be a blank string and is shown when no parameter passed.





