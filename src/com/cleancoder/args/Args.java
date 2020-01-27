package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;
/**
 * Class for argument - Schema 
 * Initializes and sets the schema and the values based on Input from the user
 */
public class Args
{
  //Map for schema and the associated values. This is first initialized with the types defined in the main code
 // Thereafter, while parsing the command prompt entry, corresponding values are assigned 	
 //Various schema types possible implement the interface
  private Map<Character, ArgumentMarshaler> marshalers;
  private Set<Character> argsFound;
  //List for the input passed from the command prompt
  private ListIterator<String> currentArgument;

  public Args(String schema, String[] args) throws ArgsException
  {
	marshalers = new HashMap<Character, ArgumentMarshaler>();
    argsFound = new HashSet<Character>();    
    parseSchema(schema);
    parseArgumentStrings(Arrays.asList(args));
  }
  
  /**
   * function to read the schema defined in the application
   * @param schema
   * @throws ArgsException
   */
  private void parseSchema(String schema) throws ArgsException
  {
    for (String element : schema.split(","))
      if (element.length() > 0)
        parseSchemaElement(element.trim());
  }
  
  /**
   * function to initialize the schema element type and assign the default  appropriate data value  
   * @param element - Schema element definition 
   * @throws ArgsException
   */
   private void parseSchemaElement(String element) throws ArgsException
  {
    char elementId = element.charAt(0);
    String elementTail = element.substring(1);
    validateSchemaElementId(elementId);
    if (elementTail.length() == 0)
      marshalers.put(elementId, new BooleanArgumentMarshaler());
    else if (elementTail.equals("*"))
      marshalers.put(elementId, new StringArgumentMarshaler());
    else if (elementTail.equals("#"))
      marshalers.put(elementId, new IntegerArgumentMarshaler());
    else if (elementTail.equals("##"))
      marshalers.put(elementId, new DoubleArgumentMarshaler());
    else if (elementTail.equals("[*]"))
      marshalers.put(elementId, new StringArrayArgumentMarshaler());
    else if (elementTail.equals("&"))
      marshalers.put(elementId, new MapArgumentMarshaler());
    else
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
  }
  
  /**
   * Function to validate schema name, only chars expected
   * @param elementId - schema element character as per the schema defined 
   * @throws ArgsException
   */
  private void validateSchemaElementId(char elementId) throws ArgsException 
  {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
  }
  
/**
 * Parse the arguments passed from the command prompt
 * @param argsList
 * @throws ArgsException
 */
  private void parseArgumentStrings(List<String> argsList) throws ArgsException
  {
	//Iterator through each element of the array list of arguments from the command prompt
	//This has the schema element id as well as the values  
    for (currentArgument = argsList.listIterator(); currentArgument.hasNext();)
    {
      String argString = currentArgument.next();
      //The string read is identified as a schema argument if it starts with a '-'
      //The var List Item currentArgument will therefore give the schema element-value pair
      if (argString.startsWith("-")) 
      {
    	//characters after '-' have the schema id and value, call function to assign the values  
        parseArgumentCharacters(argString.substring(1));
      }
      else 
      {
        //currentArgument.previous();
        //break;
    	throw new ArgsException(MALFORMED_INPUT_STRING);  
      }
    }
  }
  
/**
 * Parse argument for a particular schema flag
 * @param argChars - the argument value for the schema flag as entered at the command prompt
 * @throws ArgsException
 */
  private void parseArgumentCharacters(String argChars) throws ArgsException
  {

	  for (int i = 0; i < argChars.length(); i++)
      parseArgumentCharacter(argChars.charAt(i));
  }
/**
 * Set values passed by the user by invoking appropriate marshaler
 * @param argChar - schema element id given as input by the user
 * @throws ArgsException
 */
  private void parseArgumentCharacter(char argChar) throws ArgsException 
  {
	//Get the appropriate marshaler initialized by the schema definition in the program  
    ArgumentMarshaler m = marshalers.get(argChar);
    //If the user input schema id does not match the definition in the program
    if (m == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    } 
    //if correct input given by the user, schema input id  matches the ids defined in the program 
    else
    {
      argsFound.add(argChar);
      try 
      {
    	//Invoke the appropriate marshaler and set the values given by the user to the schema element
        m.set(currentArgument);
      } 
      catch (ArgsException e)
      {
    	//Call appropriate error defined for the particular schema argument  
        e.setErrorArgumentId(argChar);
        throw e;
      }
    }
  }

  
  public boolean has(char arg)
  {
   return argsFound.contains(arg);
  }
  
  //Region: Getters

  public int nextArgument()
  {
    return currentArgument.nextIndex();
  }

  public boolean getBoolean(char arg)
  {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String getString(char arg) 
  {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public int getInt(char arg)
  {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public double getDouble(char arg)
  {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String[] getStringArray(char arg)
  {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public Map<String, String> getMap(char arg)
  {
    return MapArgumentMarshaler.getValue(marshalers.get(arg));
  }
}