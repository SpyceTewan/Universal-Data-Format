# Universal-Data-Format
The aim of this format is to easily extract data off files.

## File format
```
keyword: value of the key
another keyword: whitespaces and special symbols like " or # don't break the thing (except for :)

arrayname {
  all values are being read until a line contains only }
  Whitespaces will not be removed anywhere (Except for the array keyname), 
  but tabulators will, so you can make your arrays more readable
}
```
You can save the file with any file extension you like, but I would prefer if you used .udf

## Parsing the files in Java
```java
File file = new File("C:/example.udf");

String string = UDF.getString(file, "another keyword");
ArrayList<String> array ) UDF.getArray(file, "arrayname");
```
