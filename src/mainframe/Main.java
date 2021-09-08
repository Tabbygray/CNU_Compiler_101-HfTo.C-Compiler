package mainframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Main Class does EVERYTHING except building output file.
// which consists reading line from input files, recognizing tokens...
// then convert .hf lines to .C grammar. and pass them to <<Class FileSpooler>>.

public class Main {
    public static void main(String[] args) {
        FileSpooler fileSpooler = new FileSpooler();
        fileSpooler.saveDataToArray("#include <stdio.h>");
        fileSpooler.saveDataToArray("#include <stdlib.h>");
        fileSpooler.saveDataToArray("int main() {");
        System.out.println(" >> Program start <<");

        try {
            File file = new File("Sample.hf"); // import text file from local drive.
            System.out.println("[-] Searching Sample File >> " + file.getAbsolutePath());
            Scanner fileScanner = new Scanner(file); // declare Scanner class.

            while (fileScanner.hasNextLine()) { // read file line by line.
                String lineBuffer = fileScanner.nextLine(); // read single line from scanner.
                Scanner lineScanner = new Scanner(lineBuffer); // this scanner will read line, token by token
                char firstCharacter = lineBuffer.charAt(0); // we need to check if the first char of line is "(" or not.
                if (firstCharacter == '(') {
                    //System.out.println("correct syntax!");
                    if (lineScanner.hasNext()) { // get each tokens from line, distinguish with spaces.
                        String firstToken_uncut = lineScanner.next(); // "example : (echo "
                        String firstToken = firstToken_uncut.substring(1); // cutting char '('
                        switch (firstToken) {
                            case "echo":
                                String tokenToCheck = lineScanner.next(); // first token of line except command
                                if (
                                        (tokenToCheck.charAt(0) == '\"') // token's first char is "
                                                && (tokenToCheck.charAt(tokenToCheck.length() - 2) == '\"'))
                                // AND token's last char also is "
                                { //this sequence is called when [echo "nospacewords"] like called.
                                    int tokenLength = tokenToCheck.length();
                                    fileSpooler.saveDataToArray("printf(\"" + tokenToCheck.substring(1, tokenLength - 2) + "\\n\");");
                                } else if (tokenToCheck.charAt(0) == '\"') { // Token has spaces inside. ex) echo "help me"
                                    String strBuffer = "";
                                    strBuffer = strBuffer.concat(tokenToCheck.substring(1) + " ");  // first token with "
                                    while (lineScanner.hasNext()) { // cycle within "~"
                                        String nextToken = lineScanner.next();
                                        int nextTokenLength = nextToken.length();
                                        if (nextToken.charAt(nextToken.length() - 2) == '\"') { // is end of token
                                            strBuffer = strBuffer.concat(nextToken.substring(0, nextTokenLength - 2));
                                        } else { // is middle token
                                            strBuffer = strBuffer.concat(nextToken + " ");
                                        }
                                    }
                                    fileSpooler.saveDataToArray("printf(\"" + strBuffer + "\\n\");");
                                } else { // token does not start with "
                                    System.out.println("ECHO command Syntax error. \" is expected.");
                                }
                                break;

                            case "del":
                                String tokenToCheck_del = lineScanner.next(); // first token of line except command
                                if (
                                        (tokenToCheck_del.charAt(0) == '\"') // token's first char is "
                                                && (tokenToCheck_del.charAt(tokenToCheck_del.length() - 2) == '\"'))
                                // AND token's last char also is "
                                { //this sequence is called when [del "nospacewords"] like called.
                                    int tokenLength = tokenToCheck_del.length();
                                    fileSpooler.saveDataToArray("system(\"del " + tokenToCheck_del.substring(1, tokenLength - 2) + "\");");
                                } else if (tokenToCheck_del.charAt(0) == '\"') { // Token has spaces inside. ex) echo "help me"
                                    String strBuffer = "";
                                    strBuffer = strBuffer.concat(tokenToCheck_del.substring(1) + " ");  // first token with "
                                    while (lineScanner.hasNext()) { // cycle within "~"
                                        String nextToken = lineScanner.next();
                                        int nextTokenLength = nextToken.length();
                                        if (nextToken.charAt(nextToken.length() - 2) == '\"') { // is end of token
                                            strBuffer = strBuffer.concat(nextToken.substring(0, nextTokenLength - 2));
                                        } else { // is middle token
                                            strBuffer = strBuffer.concat(nextToken + " ");
                                        }
                                    }
                                    fileSpooler.saveDataToArray("system(\"del \\\"" + strBuffer + "\\\"\");");
                                } else { // token does not start with "
                                    System.out.println("DEL command Syntax error. \" is expected.");
                                }
                                break;

                            case "list_dir)":
                                fileSpooler.saveDataToArray("system(\"dir\");");
                                break;

                            case "show":
                                String tokenToCheck_show = lineScanner.next(); // first token of line except command
                                if (
                                        (tokenToCheck_show.charAt(0) == '\"') // token's first char is "
                                                && (tokenToCheck_show.charAt(tokenToCheck_show.length() - 2) == '\"'))
                                // AND token's last char also is "
                                { //this sequence is called when [show "nospacewords"] like called.
                                    int tokenLength = tokenToCheck_show.length();
                                    fileSpooler.saveDataToArray("system(\"type " + tokenToCheck_show.substring(1, tokenLength - 2) + "\");");
                                } else if (tokenToCheck_show.charAt(0) == '\"') { // Token has spaces inside. ex) echo "help me"
                                    String strBuffer = "";
                                    strBuffer = strBuffer.concat(tokenToCheck_show.substring(1) + " "); // first token with "
                                    while (lineScanner.hasNext()) { // cycle within "~"
                                        String nextToken = lineScanner.next();
                                        int nextTokenLength = nextToken.length();
                                        if (nextToken.charAt(nextToken.length() - 2) == '\"') { // is end of token
                                            strBuffer = strBuffer.concat(nextToken.substring(0, nextTokenLength - 2));
                                        } else { // is middle token
                                            strBuffer = strBuffer.concat(nextToken + " ");
                                        }
                                    }
                                    fileSpooler.saveDataToArray("system(\"type \\\"" + strBuffer + "\\\"\");");
                                } else { // token does not start with "
                                    System.out.println("SHOW command Syntax error. \" is expected.");
                                }
                                break;

                            case "mov":
                                String tokenToCheck_mov = lineScanner.next(); // first token of line except command
                                switch (tokenToCheck_mov) {
                                    case "list_dir":
                                        tokenToCheck_mov = lineScanner.next(); // first token expect list_dir
                                        if (
                                                (tokenToCheck_mov.charAt(0) == '\"') // token's first char is "
                                                        && (tokenToCheck_mov.charAt(tokenToCheck_mov.length() - 2) == '\"'))
                                        // AND token's last char also is "
                                        { //this sequence is called when [mov list_dir "nospacewords"] like called.
                                            int tokenLength = tokenToCheck_mov.length();
                                            fileSpooler.saveDataToArray("system(\"dir > " + tokenToCheck_mov.substring(1, tokenLength - 2) + "\");");
                                        } else if (tokenToCheck_mov.charAt(0) == '\"') {
                                            String strBuffer = "";
                                            strBuffer = strBuffer.concat(tokenToCheck_mov.substring(1) + " ");
                                            while (lineScanner.hasNext()) { // cycle within "~"
                                                String nextToken = lineScanner.next();
                                                int nextTokenLength = nextToken.length();
                                                if (nextToken.charAt(nextToken.length() - 2) == '\"') { // is end of token
                                                    strBuffer = strBuffer.concat(nextToken.substring(0, nextTokenLength - 2));
                                                } else { // is middle token
                                                    strBuffer = strBuffer.concat(nextToken + " ");
                                                }
                                            }
                                            fileSpooler.saveDataToArray("system(\"dir > \\\"" + strBuffer + "\\\"\");");
                                        }
                                        break;
                                    case "echo":

                                        // NOTICE : DELETED DUE TO PROJECT PLAN CHANGE //
                                        // MOV - ECHO CASE is working, but not stabilized. //

                                        /*
                                        tokenToCheck_mov = lineScanner.next(); // first token except echo
                                        if (
                                                (tokenToCheck_mov.charAt(0) == '\"') // token's first char is "
                                                        && (tokenToCheck_mov.charAt(tokenToCheck_mov.length() - 1) == '\"'))
                                        // AND token's last char also is "
                                        { //this sequence is called when [mov echo "nospacewords" "????"] like called.
                                            String echoFileName = tokenToCheck_mov.substring(1, tokenToCheck_mov.length() - 1); // mov echo >>"qwer01.txt"<< res.txt
                                            tokenToCheck_mov = lineScanner.next(); // move to save filename
                                            if (
                                                    (tokenToCheck_mov.charAt(0) == '\"') // token's first char is "
                                                            && (tokenToCheck_mov.charAt(tokenToCheck_mov.length() - 2) == '\"')) {
                                                // when save file name is also no space.
                                                int tokenLength = tokenToCheck_mov.length();
                                                fileSpooler.saveDataToArray("system(\"echo " + echoFileName + " > " + tokenToCheck_mov.substring(1, tokenLength - 2) + "\");");
                                            } else if (tokenToCheck_mov.charAt(0) == '\"') { // mov echo "qwer01.txt" "have space.txt"
                                                String strBuffer = "";
                                                strBuffer = strBuffer.concat(tokenToCheck_mov.substring(1) + " ");
                                                while (lineScanner.hasNext()) { // cycle within "~"
                                                    String nextToken = lineScanner.next();
                                                    int nextTokenLength = nextToken.length();
                                                    if (nextToken.charAt(nextToken.length() - 2) == '\"') { // is end of token
                                                        strBuffer = strBuffer.concat(nextToken.substring(0, nextTokenLength - 2));
                                                    } else { // is middle token
                                                        strBuffer = strBuffer.concat(nextToken + " ");
                                                    }
                                                }
                                                int tokenLength = tokenToCheck_mov.length();
                                                fileSpooler.saveDataToArray("system(\"echo " + echoFileName + " > \\\"" + strBuffer + "\\\"\");"); //case when mov echo "nospace" "have space"
                                            }

                                        } else if (tokenToCheck_mov.charAt(0) == '\"') { // mov echo "i have space.txt" "????"
                                            String echoNameStrBuffer = "";
                                            echoNameStrBuffer = echoNameStrBuffer.concat(tokenToCheck_mov.substring(1) + " ");
                                            while (lineScanner.hasNext()) { // cycle within "~"
                                                String nextToken = lineScanner.next();
                                                int nextTokenLength = nextToken.length();
                                                if (nextToken.charAt(nextToken.length() - 1) == '\"') { // is end of token
                                                    echoNameStrBuffer = echoNameStrBuffer.concat(nextToken.substring(0, nextTokenLength - 1));
                                                    break;
                                                } else { // is middle token
                                                    echoNameStrBuffer = echoNameStrBuffer.concat(nextToken + " ");
                                                }
                                            } // end of collecting echoNameString

                                            String saveFileToken = lineScanner.next();
                                            if (
                                                    (saveFileToken.charAt(0) == '\"') // token's first char is "
                                                            && (saveFileToken.charAt(saveFileToken.length() - 2) == '\"')) { // mov echo "i have space" "nospace"
                                                int saveFileTokenLength = saveFileToken.length();
                                                fileSpooler.saveDataToArray("system(\"echo \\\"" + echoNameStrBuffer + "\\\" > " + saveFileToken.substring(1, saveFileTokenLength - 2) + "\");");
                                            } else if (saveFileToken.charAt(0) == '\"') {
                                                String saveNameStrBuffer = "";
                                                saveNameStrBuffer = saveNameStrBuffer.concat(saveFileToken.substring(1) + " ");
                                                while (lineScanner.hasNext()) { // cycle within "~"
                                                    String nextToken = lineScanner.next();
                                                    int nextTokenLength = nextToken.length();
                                                    if (nextToken.charAt(nextToken.length() - 2) == '\"') { // is end of token
                                                        saveNameStrBuffer = saveNameStrBuffer.concat(nextToken.substring(0, nextTokenLength - 2));
                                                        break;
                                                    } else { // is middle token
                                                        saveNameStrBuffer = saveNameStrBuffer.concat(nextToken + " ");
                                                    }
                                                }
                                                fileSpooler.saveDataToArray("system(\"echo \\\"" + echoNameStrBuffer + "\\\" > \\\"" + saveNameStrBuffer + "\\\"\");");
                                            }
                                        }

                                        */
                                        break;
                                    case "del":
                                        // mov supports list_dir only. //
                                        break;
                                    case "show":
                                        // mov supports list_dir only. //
                                        break;
                                    default:
                                        System.out.println("unknown command to mov");
                                }
                                break; // end of mov command //

                            default:
                                System.out.println("Unknown command.");
                                break;
                                // end of command checking //
                        }
                    } //end of token check
                } else { // line's first character is not '('
                    System.out.println("wrong syntax.");
                }
            } // end of line check
            fileSpooler.endGwalho();
            fileSpooler.writeFile();
        } catch (FileNotFoundException e) {
            System.out.println("File Not found.");
        }
    }


}
