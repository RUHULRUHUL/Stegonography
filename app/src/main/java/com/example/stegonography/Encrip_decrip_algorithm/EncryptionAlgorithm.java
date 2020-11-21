package com.example.stegonography.Encrip_decrip_algorithm;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.stegonography.timecalcu.Timecalculate;

public class EncryptionAlgorithm implements Timecalculate {

    public  Timecalculate timecalculate;
    public long starttime,endtime,finaltime;
    String padding = " ";
    // Constant Mix Column
    final String[][] mixColumn = new String[][]{
            //0    1    2    3    4    5    6    7
            /*0*/{"2", "3", "1", "1", "1", "1", "1", "3"},
            /*1*/ {"1", "2", "3", "1", "3", "2", "1", "1"},
            /*2*/ {"1", "1", "2", "3", "1", "3", "2", "1"},
            /*3*/ {"3", "1", "1", "1", "1", "1", "3", "2"},
            /*4*/ {"1", "1", "1", "3", "2", "3", "1", "1"},
            /*5*/ {"3", "2", "1", "1", "1", "2", "3", "1"},
            /*6*/ {"1", "3", "2", "1", "1", "1", "2", "3"},
            /*7*/ {"1", "1", "3", "2", "3", "1", "1", "1"},};

    public String Text = "";
    public String Key = "";
    public String plainText = "";
    public int length;
    public int block;
    String encryptedText = "";
    String encoded = "";
    String decoded = "";

    String[][][] myblock; // TextBlock Storage
    String[][][] tempkeyblock;  //Temp KeyBlock Storage
    String[][] keyblock = new String[8][8];  //Key Storage
    String[][] keyblock4 = new String[8][8];

    String encrypt(EncryptionAlgorithm ob) {

//        starttime=System.currentTimeMillis();
        System.out.println("..........................Encryption In Progress..........................");

        plainText = "";
        length = 0;
        block = 0;
        decoded = "";
        encryptedText = "";

        int loc = 0;  //for targeting char location in the Text

        ob.length = ob.Text.length();  //Plain Text Length
        System.out.println("Plain Text: " + ob.Text);
        System.out.println("       Key: " + ob.Key);

        //generating no of block for TEXT
        ob.block = (int) Math.ceil((double) ob.length / 64);
        System.out.println("Total Text Block = " + ob.block);
        int tempBlock = 1;  //Blocks can be multiple. tempBlock will hold current block no (Will be adjusted to tempBlock-1)

        //initializing myblock multiple blocks (3d matrix)
        myblock = new String[ob.block][8][8];

        //generating no of block for KEY
        int keyBlocks = (int) Math.ceil((double) ob.Key.length() / 16);
        System.out.println("Total Key Block = " + keyBlocks);  //printing no of block

        //initialize tempkeyblock multiple blocks (3d matrix)
        tempkeyblock = new String[keyBlocks][4][4];

        keyManipulation(keyBlocks, ob);
        //first block then second block.................
        System.out.println("Processing Plain Text.....");
        while (tempBlock <= ob.block) {
            System.out.println("Processing Text Block " + tempBlock + " ......");
            //inserting characters into block//
            for (int second = 0; second < 8; second++) {  //row
                for (int third = 0; third < 8; third++) {     //column
                    if (loc < ob.length) {  // if string index exceded, pading is required
                        /*
                            characters are converted into string
                         */

                        myblock[tempBlock - 1][second][third] = Character.toString(ob.Text.charAt(loc));
                        loc++;  //incrementing last char pos in the Text
                    } else {
                        myblock[tempBlock - 1][second][third] = padding;  //pading started
                    }
                }
            }
            //end of assigning chars into block

            //printing the matrixes
            System.out.println("Text Block : " + tempBlock);
            printArray(tempBlock - 1);
            //end of print matrix

            //converting to ASCII
            convertToAscii(tempBlock - 1);
            //end of ASCII convert

            //printing the matrix
//            System.out.println("\n Converted to ASCII:");
//            printArray(tempBlock - 1);
            // In hex
            System.out.println("Text Block In HEX :");
            printArrayInHex(myblock[tempBlock - 1]);

            // In hex
            System.out.println("Key Block In HEx :");
            printArrayInHex(keyblock);

            //XOR between TextBlock and KeyBlock
            performXorBetweenTextAndKey(tempBlock - 1);
            //end of XOR

            // In hex
            System.out.println("Text Block XOR Key Block :");
            printArrayInHex(myblock[tempBlock - 1]);

            //printing matrix
//            String[][] c1 = Arrays.stream(myblock[tempBlock - 1]).map(String[]::clone).toArray(String[][]::new);
//            String[][] aaaa = convertMyTempBlockIntoHexa(c1);
//            System.out.println("XOR beteen TextBlock and KeyBlock: ");
//            printKeyArray(aaaa);
            //end of print
            for (int times = 0; times < 4; times++) {
                System.out.println("Starting Round : " + (times + 1));
                //permutation part started//
                //interchanging between columns (I1)
                interchangeC1andC3(tempBlock - 1);
                interchangeC2andC4(tempBlock - 1);
                interchangeC5andC7(tempBlock - 1);
                interchangeC6andC8(tempBlock - 1);
                //end of interchange column

                //printing the matrix
                System.out.println("\nColumns Interchange (I1) :");
                printArrayInHex(myblock[tempBlock - 1]);
                //printArray(tempBlock - 1);

                //reversing the block
                reverseTwoDimentionalArray(tempBlock - 1);
                //reverse completed
                System.out.println("\nReversed Block:");
                printArrayInHex(myblock[tempBlock - 1]);

                //interchanging between rows (I2)
                interchangeR1andR3(tempBlock - 1);
                interchangeR2andR4(tempBlock - 1);
                interchangeR5andR7(tempBlock - 1);
                interchangeR6andR8(tempBlock - 1);
                //end of interchange rows

                //printing the matrix
                System.out.println("\nRows Interchange (I2) :");
                printArrayInHex(myblock[tempBlock - 1]);
                //matrix printed

                // Xor between Interchange 2 and Mix Column
                performXorBetweenI2AndMixColumn(tempBlock - 1);
                //Xor performed

                // In hex
                System.out.println("\nI2 XOR Mix Column:");
                printArrayInHex(myblock[tempBlock - 1]);

                //convert into Hexadecimal
                convertMyBlockIntoHexa(tempBlock - 1);
                //converted completed

                //Apply SBox
                replaceBySBox(tempBlock - 1);
                //Complete

                System.out.println("Convert Into SBOX:");
                printArray(tempBlock - 1);

                //Convert myBlock into Decimal
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    convertBlockToAsciiFromHex(tempBlock - 1);
                }

                //myblock xor Key
                performXorBetweenTextAndKey(tempBlock - 1);

                //printing matrix
                System.out.println("\nConverted SBOX XOR KEY:");
                printArrayInHex(myblock[tempBlock - 1]);
                //end of print
                System.out.println("Finished Round : " + (times + 1));
            }
            System.out.println("4 Round Completed.");
            convertMyBlockIntoHexa(tempBlock - 1);
            System.out.println("Final Block for Text Block " + (tempBlock));
            printArray(tempBlock - 1);
            tempBlock++;
        }

        //arranging into plain text
        for (int i = 0; i < ob.block; i++) {
            for (int second = 0; second < 8; second++) {
                for (int third = 0; third < 8; third++) {
                    if (myblock[i][second][third].length() < 2) {
                        myblock[i][second][third] = "0" + myblock[i][second][third];
                    }
                    ob.plainText = ob.plainText + " " + myblock[i][second][third];
                }
            }

        }





        //Final Pass
        ob.plainText = ob.plainText.substring(1, ob.plainText.length());
        System.out.println("\nFinal Encrypted Text: " + ob.plainText);
        ob.encoded = ob.plainText;  // We did not encode text in this algorithm
        System.out.println("..........................Encryption Finished..........................");

        //endtime=System.currentTimeMillis();
//        finaltime=starttime;
//        gettiime(finaltime);

        return ob.encoded;

    }

    String decrypt(EncryptionAlgorithm ob) {

        starttime=System.currentTimeMillis();

        System.out.println("..........................Decryption In Progress..........................");

        //Text was never encoded, So copying text in proper place without decoding
        //decoding
        //ob.decodeMyData();
        ob.decoded = ob.encoded; // Data is already decoded (was Never encoded)

        System.out.println("Encrypted Text: " + ob.decoded);
        System.out.println("           Key: " + ob.Key);

        plainText = "";

        int start = 0, end = 2;

        int totalChars = 0;
        //counting total cells
        for (int i = 0; i < ob.decoded.length(); i++) {
            if (ob.decoded.charAt(i) != ' ') {
                totalChars++;
            }
        }
        //as each data is two chars long so /2
        int cells = totalChars / 2;
        //final total block needed
        ob.block = (int) Math.ceil((double) cells / 64);
        myblock = new String[ob.block][8][8];//String array, because of the Hex values will be string

        //generating no of block for key
        int keyBlocks = (int) Math.ceil((double) ob.Key.length() / 16);
        System.out.println("Total Text Block = " + ob.block);
        System.out.println("Total Key Block = " + keyBlocks);  //printing no of block

        //assigning Text Chars into array
        for (int i = 0; i < ob.block; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    if (start < decoded.length()) {

                        myblock[i][j][k] = ob.decoded.substring(start, end);

                        start = end + 1;
                        end = start + 2;

                    } else {
                        myblock[i][j][k] = padding;
                    }

                }
            }
        }
        //assigned

        //printing matrix
        System.out.println("All Text Blocks :");
        printDecryptArray();
        //end of print
        //initialize tempkey array
        tempkeyblock = new String[keyBlocks][4][4];

        keyManipulation(keyBlocks, ob);

        int tempBlock = 1;
        //block loop started
        System.out.println("Processing Encrypted Text.....");
        while (tempBlock <= ob.block) {
            System.out.println("Processing Text Block " + tempBlock + " ......");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                convertBlockToAsciiFromHex(tempBlock - 1);
            }

            System.out.println("Text Block : " + tempBlock);
            printArrayInHex(myblock[tempBlock - 1]);

            System.out.println("Key Block :");
            printArrayInHex(keyblock);
            for (int times = 0; times < 4; times++) {
                System.out.println("Starting Round : " + (times + 1));

                //Already converted before
//                System.out.println("Convert KeyBlock to ascii");
//                printArrayInHex(keyblock);
                //Text Xor Key
                performXorBetweenTextAndKey(tempBlock - 1);

                System.out.println("Text Block XOR Key Block");
                printArrayInHex(myblock[tempBlock - 1]);

                // Converting Text Block into Hexa for SBoX
                convertMyBlockIntoHexa(tempBlock - 1);

                //replace values using S-Box
                replaceByReverseSBox(tempBlock - 1);
                //replace completed

                //printing matrix
                System.out.println("Convert Into Reverse SBOX: ");
                printArray(tempBlock - 1);
                //end of print

                //convert into Decimal ASCII
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    convertBlockToAsciiFromHex(tempBlock - 1);
                }
                //convert completed

                //printing matrix
//                System.out.println("Converted into ASCII: ");
//                printArray(tempBlock - 1);
                //end of print
                // Text Xor MixColumn
                performXorBetweenI2AndMixColumn(tempBlock - 1);
                // xor complete

                System.out.println("Reversed SBOX XOR MixColumn :");
                printArrayInHex(myblock[tempBlock - 1]);

                //Perform Interchange 2
                //interchanging between rows (I2)
                interchangeR1andR3(tempBlock - 1);
                interchangeR2andR4(tempBlock - 1);
                interchangeR5andR7(tempBlock - 1);
                interchangeR6andR8(tempBlock - 1);
                //end of interchange rows

                // In hex
                System.out.println("Rows Interchange (I2) :");
                printArrayInHex(myblock[tempBlock - 1]);
                // Xor between Interchange 2 and Mix Column

                //reversing the block
                reverseTwoDimentionalArray(tempBlock - 1);
                //reverse completed
                System.out.println("Reversed Block:");
                printArrayInHex(myblock[tempBlock - 1]);

                //interchanging between columns (I1)
                interchangeC1andC3(tempBlock - 1);
                interchangeC2andC4(tempBlock - 1);
                interchangeC5andC7(tempBlock - 1);
                interchangeC6andC8(tempBlock - 1);
                //end of interchange column

                //printing the matrix
                System.out.println("Columns Interchange (I1) :");
                printArrayInHex(myblock[tempBlock - 1]);
                //printArray(tempBlock - 1);
                System.out.println("Finished Round : " + (times + 1));
            }
            System.out.println("4 Round Completed.");

            // I1 Xor key
            performXorBetweenTextAndKey(tempBlock - 1);
            // completed

            //printing the matrix
            System.out.println("Text Block XOR Key Block :");
            printArrayInHex(myblock[tempBlock - 1]);

            //printArray(tempBlock - 1);
            //converting to ASCII
            for (int second = 0; second < 8; second++) {
                for (int third = 0; third < 8; third++) {

                    myblock[tempBlock - 1][second][third] = Character.toString((char) Integer.parseInt(myblock[tempBlock - 1][second][third]));

                }

            }
            //end of ASCII convert

            //printing the matrix
            System.out.println("HEX to Character : ( Final Block for Text Block " + tempBlock + " )");
            printArray(tempBlock - 1);
            //print end

            //arranging into plain text
            for (int second = 0; second < 8; second++) {
                for (int third = 0; third < 8; third++) {
                    ob.plainText = ob.plainText + "" + myblock[tempBlock - 1][second][third];
                }
            }

            tempBlock++;
        }

        endtime=System.currentTimeMillis();
        finaltime=starttime+endtime;
        gettiime(finaltime);

        System.out.println("Final Decrypted Text: " + ob.plainText);
        System.out.println("..........................Decryption Finished..........................");
        return ob.plainText;

    }

    void replaceByReverseSBox(int tempblock) {
        /*
        Reverse the Values of Text Block with Reverse SBOX
         */
        Sbox box = new Sbox();
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {

                myblock[tempblock][second][third] = box.getReverseSBoxValue(myblock[tempblock][second][third]).toUpperCase();

            }
        }
    }

    void printDecryptArray() {
        /*
        Print all block of Text Block
         */
        for (int block = 0; block < this.block; block++) {
            for (int second = 0; second < 8; second++) {
                for (int third = 0; third < 8; third++) {
                    System.out.print(myblock[block][second][third] + ",");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println(".................");
    }

    void convertToAscii(int tempblock) {
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {

                myblock[tempblock][second][third] = Integer.toString((int) (myblock[tempblock][second][third].charAt(0)));

            }

        }
    }

    void replaceBySBox(int tempblock) {
        /*
        Udpate TextBlock cells with SBOX values
         */
        Sbox box = new Sbox();
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                myblock[tempblock][second][third] = box.getSBoxValue(myblock[tempblock][second][third]);

            }

        }
    }

    void interchangeC1andC3(int tempblock) {
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third <= 0; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second][third + 2];
                myblock[tempblock][second][third + 2] = temp;
            }
        }
    }

    void interchangeC2andC4(int tempblock) {
        for (int second = 0; second < 8; second++) {
            for (int third = 1; third <= 1; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second][third + 2];
                myblock[tempblock][second][third + 2] = temp;
            }
        }
    }

    void interchangeC5andC7(int tempblock) {
        for (int second = 0; second < 8; second++) {
            for (int third = 4; third <= 4; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second][third + 2];
                myblock[tempblock][second][third + 2] = temp;
            }
        }
    }

    void interchangeC6andC8(int tempblock) {
        for (int second = 0; second < 8; second++) {
            for (int third = 5; third <= 5; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second][third + 2];
                myblock[tempblock][second][third + 2] = temp;
            }
        }
    }

    void reverseTwoDimentionalArray(int tempblock) {
        //reverse first phase: reverse columns
        for (int second = 0; second < 8; second++) {
            int first = 0, last = 7;
            for (int third = 0; third < 8 / 2; third++) {

                String temp1 = myblock[tempblock][second][first];
                myblock[tempblock][second][first] = myblock[tempblock][second][last];
                myblock[tempblock][second][last] = temp1;
                first++;
                last--;
                if (first > last) {
                    break;
                }
            }

        }

        //reverse first phase: reverse rows
        int first = 0, last = 7;
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                String temp1 = myblock[tempblock][first][third];
                myblock[tempblock][first][third] = myblock[tempblock][last][third];
                myblock[tempblock][last][third] = temp1;
            }
            first++;
            last--;
            if (first > last) {
                break;
            }

        }
    }

    void interchangeR1andR3(int tempblock) {
        for (int second = 0; second <= 0; second++) {
            for (int third = 0; third < 8; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second + 2][third];
                myblock[tempblock][second + 2][third] = temp;
            }

        }
    }

    void interchangeR2andR4(int tempblock) {
        for (int second = 1; second <= 1; second++) {
            for (int third = 0; third < 8; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second + 2][third];
                myblock[tempblock][second + 2][third] = temp;
            }

        }
    }

    void interchangeR5andR7(int tempblock) {
        for (int second = 4; second <= 4; second++) {
            for (int third = 0; third < 8; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second + 2][third];
                myblock[tempblock][second + 2][third] = temp;
            }

        }
    }

    void interchangeR6andR8(int tempblock) {
        for (int second = 5; second <= 5; second++) {
            for (int third = 0; third < 8; third++) {
                String temp = myblock[tempblock][second][third];
                myblock[tempblock][second][third] = myblock[tempblock][second + 2][third];
                myblock[tempblock][second + 2][third] = temp;
            }
        }
    }

    void performXorBetweenI2AndMixColumn(int tempblock) {
        /*
            Xor between TextBlock and MixColumn
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                int c3 = Integer.parseInt(myblock[tempblock][second][third]);
                int c4 = Integer.parseInt(mixColumn[second][third]);
                myblock[tempblock][second][third] = Integer.toString(c3 ^ c4);

            }

        }
    }

    void printArray(int tempblock) {
        /*
        Print Text Block
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                System.out.print(myblock[tempblock][second][third] + ",");
            }
            System.out.println();
        }
        System.out.println(".................");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void convertBlockToAsciiFromHex(int tempblock) {
        /*
       Convert TextBlock from Hex to Decimal
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                myblock[tempblock][second][third] = Integer.toString(Integer.parseUnsignedInt(myblock[tempblock][second][third], 16));
            }
        }
    }

    void performXorBetweenTextAndKey(int tempblock) {
        /*
        8*8 Text Block XOR Key Block
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                int c3 = Integer.parseInt(myblock[tempblock][second][third]);
                int c4 = Integer.parseInt(keyblock[second][third]);
                myblock[tempblock][second][third] = Integer.toString(c3 ^ c4);
            }

        }
    }

    void convertMyBlockIntoHexa(int tempblock) {
        /*
        Convert Text block into Hex from Decimal String
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                myblock[tempblock][second][third] = Integer.toHexString(Integer.parseInt(myblock[tempblock][second][third])).toUpperCase();
            }

        }
    }

    private void keyManipulation(int keyBlocks, EncryptionAlgorithm ob) {
        System.out.println("Key Processing started...");
        //KEY MODIFICATION
        int keyLoc = 0;
        String[][] temp4 = new String[4][4];

        //Create 4 * 4 blocs of key chars
        for (int kBn = 0; kBn < keyBlocks; kBn++) {
            System.out.println("Key Block: " + (kBn + 1));

            for (int second = 0; second < 4; second++) {  //second dimension
                for (int third = 0; third < 4; third++) {     //third dimension
                    if (keyLoc < ob.Key.length()) {  // if string index exceded, pading is required
                        tempkeyblock[kBn][second][third] = Character.toString(ob.Key.charAt(keyLoc));
                        System.out.print(Character.toString(ob.Key.charAt(keyLoc)) + ",");
                        keyLoc++;  //incrementing last char pos in the Text
                    } else {
                        System.out.print(padding + ",");
                        tempkeyblock[kBn][second][third] = padding;  //pading started
                    }
                }
                System.out.println();
            }
        }

        // Char to ASCII
        for (int kBn = 0; kBn < keyBlocks; kBn++) {
            convertTempKeyToASCII(kBn);
        }


        //Convert 4 * 4 Key to Hex
        for (int kBn = 0; kBn < keyBlocks; kBn++) {
            tempkeyblock[kBn] = convertMyTempBlockIntoHexa44(tempkeyblock[kBn].clone());
            System.out.println("Key Block "+kBn+" in HEX :");
            printTempKey(kBn);
        }

        // Performing xor among 4*4 keys
        for (int kBn = 0; kBn < keyBlocks; kBn++) {
            if (kBn == 0) {
                keyblock4 = tempkeyblock[kBn];

            } else {
                performXorBetweenKeyAndKey(tempkeyblock[kBn]);

            }
        }

        System.out.println("After XOR Between Key Blocks :");
        //Shift Key prints before shift Array. So we dont need to print again


        //Shift key on final 4*4 matrix (keyblock4)
        shiftKey();

        String[][] temp8 = new String[8][8];

        // Left 1st splits of 4*4 filling
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                temp8[row][column] = keyblock4[row][column];
            }
        }

        // Left 2nd splits of 4*4 filling by reversing the given matrix
        String[][] reversed = getreverseTwoDimentionalArray(keyblock4.clone());
        for (int row = 4; row < 8; row++) {
            for (int column = 0; column < 4; column++) {
                temp8[row][column] = reversed[row - 4][column];
            }
        }

        // Right 1st splits of 4*4 filling reversed matrix
        for (int row = 0; row < 4; row++) {
            for (int column = 4; column < 8; column++) {
                temp8[row][column] = reversed[row][column - 4];
            }
        }

        // Right 2nd splits of 4*4 filling normal
        for (int row = 4; row < 8; row++) {
            for (int column = 4; column < 8; column++) {
                temp8[row][column] = temp8[row - 4][column - 4];
            }
        }
        // Why the following line updates temp8 in place?

        //String[][] temp111 = convertMyTempBlockIntoHexa(temp8.clone());
        System.out.println("Key Expand:");
        printKeyArray(temp8);


        temp8 = replaceKeyBySBox(temp8.clone());

        System.out.println("SBOX Convert:");
        printKeyArray(temp8);

        temp8 = getreverseTwoDimentionalArray(temp8.clone());
        keyblock = temp8;

        System.out.println("Reversed KeyBlock:");
        printKeyArray(temp8);

        //end of assigning chars into block
        //printing key matrix
        System.out.println("Final KeyBlock:");
        printKeyArray();
        //matrix printed

        //converting key to ASCII
        convertKeyToAsciiFromHex();
        //end of ASCII convert

        //printing key matrix
//        System.out.println("Converted to ASCII");
//        printKeyArray();
        //matrix printed
//        System.out.println("View to HExa");
//        printArrayInHex(keyblock);
        System.out.println("Key Processing Finished...");
    }

    void shiftKey() {
        /*
        Shift rows of 4*4 2d matrix (tempKeyBlock)by 0,1,2,3 respectbly.
         */

        //Before Shifting
        for (int row = 0; row < 4; row++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(keyblock4[row][j] + ",");
            }
            System.out.println();
        }


        System.out.println("Shift Key:");
        for (int row = 0; row < 4; row++) {
            for (int j = 0; j < row; j++) {
                String temp;
                temp = keyblock4[row][0];

                for (int i = 0; i < 4 - 1; i++) {
                    keyblock4[row][i] = keyblock4[row][i + 1];
                }

                keyblock4[row][4 - 1] = temp;
            }

        }

        //Printing after shifting
        for (int row = 0; row < 4; row++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(keyblock4[row][j] + ",");
            }
            System.out.println();
        }
    }

    void printKeyArray(String[][] p) {
        /*
        Receives a 2d 8*8 matrix and print it
         */
        for (int first = 0; first < 8; first++) {
            for (int second = 0; second < 8; second++) {
                System.out.print(p[first][second] + ",");
            }
            System.out.println();
        }

        System.out.println("............");
    }

    String[][] convertMyTempBlockIntoHexa(String[][] t8) {
        /*
            Receives a 2d 8*8 matrix and returns the values converted from Decimal to HEX
            This method has a problems while passing matrix as param by ob.clone(). It updates the value in place
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                t8[second][third] = Integer.toHexString(Integer.parseInt(t8[second][third])).toUpperCase();
            }
        }
        return t8;
    }

    String[][] convertMyTempBlockIntoHexa44(String[][] t8) {
        /*
            Receives a 2d 8*8 matrix and returns the values converted from Decimal to HEX
            This method has a problems while passing matrix as param by ob.clone(). It updates the value in place
         */
        for (int second = 0; second < 4; second++) {
            for (int third = 0; third < 4; third++) {
                t8[second][third] = Integer.toHexString(Integer.parseInt(t8[second][third])).toUpperCase();
            }
        }
        return t8;
    }

    String[][] replaceKeyBySBox(String[][] tempkeyBlock) {
        /*
        Receives 2d 8*8 matrix and returns a new matrix by appling SBOX
         */
        Sbox box = new Sbox();
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                tempkeyBlock[second][third] = box.getSBoxValue(tempkeyBlock[second][third]);
            }
        }
        return tempkeyBlock;
    }

    String[][] getreverseTwoDimentionalArray(String[][] array) {
        /*
        Returns given 2d array in reverse order
         */

        //reverse first phase: reverse columns
        int len = array[0].length;
        for (int second = 0; second < len; second++) {
            int first = 0, last = len - 1;
            for (int third = 0; third < len / 2; third++) {

                String temp1 = array[second][first];
                array[second][first] = array[second][last];
                array[second][last] = temp1;
                first++;
                last--;
                if (first > last) {
                    break;
                }
            }

        }

        //reverse first phase: reverse rows
        int first = 0, last = len - 1;
        for (int second = 0; second < len; second++) {
            for (int third = 0; third < len; third++) {
                String temp1 = array[first][third];
                array[first][third] = array[last][third];
                array[last][third] = temp1;
            }
            first++;
            last--;
            if (first > last) {
                break;
            }

        }

        return array;
    }

    void performXorBetweenKeyAndKey(String[][] current) {
        /*
        Xor between current 8*8 matrix (2d) with KeyBlock and Update in place
         */

        for (int second = 0; second < 4; second++) {
            for (int third = 0; third < 4; third++) {
                // Converting from hex to ascii then XOR then ascii to hex (Previous number system)
                int c3 = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    c3 = Integer.parseUnsignedInt(keyblock4[second][third], 16);
                }
                int c4 = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    c4 = Integer.parseUnsignedInt(current[second][third], 16);
                }
                keyblock4[second][third] = Integer.toHexString(Integer.parseInt(Integer.toString(c3 ^ c4)));

            }

        }
    }

    void convertKeyToAsciiFromHex() {
        /*
        Convert KeyBlock values into Decimal considering that KeyBlock is already in HEX numbersystem
         */
        for (int second = 0; second < 8; second++) {
            for (int third = 0; third < 8; third++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    keyblock[second][third] = Integer.toString(Integer.parseUnsignedInt(keyblock[second][third], 16));
                }
            }
        }
    }

    void printKeyArray() {
        /*
        Print 2d KeyBlock as it is
         */
        for (int first = 0; first < 8; first++) {
            for (int second = 0; second < 8; second++) {
                System.out.print(keyblock[first][second] + ",");
            }
            System.out.println();
        }

        System.out.println("............");
    }

    void printArrayInHex(String[][] p) {
        /*
        Receives Two Dimentional Array where values are in Decimal Strings
        Prints Decimal values as Hex
         */

        for (int first = 0; first < 8; first++) {
            for (int second = 0; second < 8; second++) {
                System.out.print(Integer.toHexString(Integer.parseInt(p[first][second])).toUpperCase() + ",");
            }
            System.out.println();
        }

        System.out.println("............");
    }

    private void convertTempKeyToASCII(int kBn) {
        for (int second = 0; second < 4; second++) {
            for (int third = 0; third < 4; third++) {

                tempkeyblock[kBn][second][third] = Integer.toString((int) (tempkeyblock[kBn][second][third].charAt(0)));

            }

        }
    }

    private void printTempKey(int kBn) {
        for (int first = 0; first < 4; first++) {
            for (int second = 0; second < 4; second++) {
                System.out.print(tempkeyblock[kBn][first][second] + ",");
            }
            System.out.println();
        }

        System.out.println("............");
    }

    @Override
    public long gettiime(long time) {
        return time;

    }

//    public static void main(String[] args) {
//        EncryptionAlgorithm ob2 = new EncryptionAlgorithm();
//        EncryptionAlgorithm ob = new EncryptionAlgorithm();
//        ob.Text = "steganography is the process of hiding a file within another file";
//        ob.Key = "Network Security";
//        long start = System.currentTimeMillis();
//        ob2.encoded = ob.encrypt(ob);
//        long end = System.currentTimeMillis();
//        System.out.println("Encryption Time in MS: "+(end-start));
//
//
//
//        System.out.println("\n\n");
//        ob2.Key = ob.Key;
//        start = System.currentTimeMillis();
//        ob2.decrypt(ob2);
//        end = System.currentTimeMillis();
//        System.out.println("Decryption Time in MS: "+(end-start));
//
//    }
}
