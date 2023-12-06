package edu.iastate.cs228.hw4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class MsgTreeTest {
        @Test
        public void testDecodeCadbard() {
                MsgTree tree = new MsgTree("^a^^!^dc^rb");

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                System.setOut(new PrintStream(output));

                MsgTree.decode(tree, "10110101011101101010100");

                assertEquals("cadbard!", output.toString());
        }

        @Test
        public void testDecodeTwoCities() {
                MsgTree tree = new MsgTree("^^ ^e^h^^gd^^^kI^yul^^^a^,^^bmp^oi^^^^\n"
                                + "^c.f^^rnw^st");

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                System.setOut(new PrintStream(output));

                MsgTree.decode(tree,
                                "0111100111110011011100011100011110110010001001100010111011110010101100100111110111001101010111010010001011111100110111000111000111101100100011011101011010011101111001010110010011111011100110101011101001000101111110011011100011101100001111011001000100001110001000101011001001101110111110011101101010011011001000101111110011011100011100011110110010001000011100010001010110010011001101010100111111011111001101101010101110111010010001011111100110111000111000111101100101100000101001111010110001001100010101100100100110001001111110110101100110010001011111100110111000111000111101100100001010011110101100010011000101011001001011110101110001011010001001110101111011011111101111110111101010010001011111100110111000111000111101100101100001110010100011101010110101001010110010001111110110111000110111110010001011111100110111000111000111101100100011100101000111010101101010010101100100011101100011010001111000110101010111011101001000101111110011011100011100011110110010001100001110100111110100101111010101110000101011001000110101010011101010010001011111100110111000111000111101100100011011101111010111110101101000010101100100011101010111010011110001011110100110001111000111100011110000");

                assertEquals("It was the best of times, it was the worst of times, it was\n" +
                                "the age of wisdom, it was the age of foolishness, it was the\n" +
                                "epoch of belief, it was the epoch of incredulity, it was the\n" +
                                "season of light, it was the season of darkness, it was the \n" +
                                "spring of hope, it was the winter of despair...\n", output.toString());
        }

        @Test
        public void testDecodeMonalisa() {
                MsgTree tree = new MsgTree("^8^ ^Z^^^\n" +
                                "^^`^^^^ro^xu^^i]^nmW^'^M-^,^^ba^\"^P^^<^)^VN^(^e>^^l^.^_^d^Y^|@^I;");

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                System.setOut(new PrintStream(output));

                MsgTree.decode(tree,
                                "101010101010101010101010101010101010101010101010101010101010101010111101101111011011110110111101101111011011110110111101101110001010101010101010101010101010101010101010101010101010111101101110101110101110110111110111000000000001110110011101100111011011110101111011011100010101010101010101010101010101010101010101010101110101110110111110111000000111110000000000000000111011001110110111101011100010101010101010101010101010101010101010101011101000000000111110000000000000000000001110110111101011100010101010101010101010101010101010101010111010111101110000000000111110000000000000000000000011101100111010111000101010101010101010101010101010101010111101110000001110111101110111101110111011101110111011101110111010111011101110111011110111101111011110000000000000000000001110110011101011100010101010101010101010101010101010111010111101110001110111011100110111101101111011011101011101011100111111100111111100111111100111111100111111100111111100111111100111111101011101011101011101011110101111111101101101111011110000000000000011101011100010101010101010101010101010101011101001111101111101111001110011011101110101010101010101010101010101010101111111111111111001110111011011011011111011111011111000000000001110101110001010101010101010101010101010111010111110001111001111111110011010101010101010101010101010101010101011111111110011011011011011000011111011111011111000000001110101110001010101010101010101010101110101111101111100011011110011111111110101010101010101010101010101010101010101111111111001111001101101101101100000001111100000001110101110001010101010101010101010111010111110111110000110111100111111111101010101010101010101010101010101010111101011111111111111111111111111111111110011110011110011011011000000011111000001110110011100010101010101010101010111010111110111110000011011111111111110101010101010101010101010101010101110010011111111111111111111111111111111100110111001101111001111001101100000000111110000011101011100010101010101010101010111110111110000001101111111110011010101010101010101010101010101010101010101010101011110101111111111001101101100000000111110000111011001110001010101010101010101011111011111000000110111111101111011011101011101101111011011110110111101010101010101011110101110101110110111101101111011011110110111101101111010111101101111011011110101111001111111111001111001101101100000000011111000011100010101010101010101010111110111110000001111101101101101101101101101101101110101010111101011011011011011011011011011011011011011011011111111110011110011011000000000111110000111010111000101010101010101010101111101111100000011111011011011101111100111001101110111111011110111111111101111111110111111111101111011111010101111011111011011011011101111100111001101110111111011110111111111101111111110111111111101101101101111111111111111001111001101100000000001111100011111011100010101010101010101011101011111011111000000111111101010111001001110111011101110111011101011111111110111110101011110111110110110111111101110010011101110111011101110111010101010101111111111111111001111001100000000000111110000111000101010101010101010111110111110000000111100101010101010101010101010111001001111111111111010101010101010101011110101111111111001111001101100000000000111110000111010111000101010101010101011101011111011111000000011011111110101010101010101010101111111111111111111010101010101010111101011111111111111110011110011011011000000000001111100001111101110001010101010101010111110111110111110000000110111100111111101010101111010111101011101010101011100100111111111111101010101010101110101111111111111111001111001111001101101100000000000011111000011100010101010101010101111101111100000000011011111111111111110101111010111101011111111101111110111101101010101011110110111011111010101010101010111010111111111111111111111100111100110110110110000000000001111100001110101110001010101010101010111110111110000000001101111001111111111111111111111111111111110011010111001001110011111110011111110011011011111111110101010101111010111010111111111111111111111111111100111100110110110110000000000001111100001110110011100010101010101010101110010101011111100000000001101111111111111111111111111110011010101011101110111111111100111100111100111100111100111100111111111101011110101111111111111111111111001111001111001101101101100000000000011111000001110101110001010101010101010111110111110000000000110111100111101011111111111111101110111101111000111011001111011100001110111101110111011111111111111101011110101111010111111111100111100111100110110110110110000000000001111100000111110111000101010101010101011111011111000000000001101111001111111111010111111101110010011101110111011110111011110111011110111011101111111111111111111110101111010111101011111111110011110011110011011011011011011011000000000000111110000001110001010101010101010111110111110000000000000110111100111111111111111101010111001001111111111111111111111001111111111111111111111111111001111001111001101101101101101101101101110010110000000000011111000000111000101010101010101011100100111110111110000000000000011011110011111111110101010101011101011111111111111110011110011110011011011011011011011011011100101111100111011000000000000111110000001110001010101010101010101111101111100000000000000000110111011001110110111101101111100111100111100110110110110110110110110110111001011111001011111001110110110110000000000011111000000011101011100010101010101010101011100100111110111110000000000000000001110110011101110111001011111001011110110110110110111001011111001011111001011111001110111001110110110110110110110111110000000000111110000000111011001110001010101010101010101011100100111110111110000000000000000001111111101101110011101110011101110011101110011101110011101110011101101101101101101101101101111001111001111100000000001111100000000111000101010101010101010101011100100111110111110000000000000000010111001001111111111001101101101101101101101101101101101111001111001111001111001111000000000001111100000000111010111000101010101010101010101010111110111110000000000000000011101010111001001111111111001111001111001101101101101111001111001111001111001111001111001111111111111111010111101111000000000111110000000011101100111010111000101010101010101010101011101011111011111000000000000000001110110010101011110101111111111111111001111001111001111001111001111001111001111111111111111111111010111111111101011110100000000011111000000000111011001110101110001010101010101010101010111110111110000000000000000111011110110111110111111111101010101111010111001001111111111111111111111010111111111111111111111101011110101111111011110101111010111101000000000111110000000000011101011100010101010101010101010101111101111100000000000001110111101101111111111111110011011111111111111110101010101111111111010101111010111111111101010101111010111111111101010111101011110101011110111100000000111110000000000001110110011101011100010101010101010101010111010111110111110000000000111011110110111111111111111001101010101010101010101010101010101010101010101010101110010000000001111100000000000000111011001110101110001010101010101010101011111011111000000000011100110101010101010101010101010101010101010101010101010101010101010000000111110000000000000000111011001110001010101010101010101110101111101111100000000001010101010101010101010101010101010101010101010101010101010101110100000001111100000000000000000111000101010101010101011101011110111000000000000101010101010101010101010101010101010101010101010101010101010111101110000000111110000000000011011011011011011011100010101010101110101110110111110111000000000000011111010101010101010101010101010101010101010101010101010101010101000000001111100000110110110110110110110110110110110110111000101010111010111101110000000000000000111001101010101010101010101010101010101010101010101010101010101010100000001111101101101101101101101101101101101101101101101101101101110001011101011110111000000000000011101111011100110011101111011100110101010101010101010101010101010101010101010101010101010101010101111011110000110110110110110110110110110110110110110110110110110110110110111000111010000000000000011101010101110111010101010101010101010101010101010101010101010101010101010101010101011101011011011011011011011011011011011011011011011011011011011011011011011011100011110111000000000000000111010101010101010101010101010101010101010101010101010101010101010101011101011011011011011011011011011011011011011011011011011011011011011011011011011011011100000000000000000000111011011110101010101010101111011010101010101010101010101010101010101010101110101101101101101101101101101101101101101101101101101101101101100000000011100000000000000000000000111011001110110111101011110110111101110111001101010101010101010101010101010101010101110101101101101101101101101101101101101101101101101101100000000000000111000000000000000000000000000000111011001110110011101100111011011110110111101101111010111010111010111101101111011011110110111101101111011011110110111010110110110110110110110110110110110110110110110000000000000000001110000000000000000000000000000000000000000000110110110110110110110110110110110110110110110000000000000000000001110000000000000000000000000000000000000000001101101101101101101101101101101101101101100000000000000000000000111000000000000000000000000000000000000000001101101101101101101101101101101101101101100000000000000000000000011100000000000000000000000000000000000000011011011011011011011011011011011011011011000000000000000000000000001110000000000000000000000000000000000000110110110110110110110110110110110110110110000000000000000000000000000111000000000000000000000000000000000000110110110110110110110110110110110110110110000000000000000010111011111011111100101000111100101000011100101011111101101111001010110111101110101000111000000000000000000000000000000000011011011011011011011011011011011011011011000000000000000000010111011111011011101111111011100101010011110011110011101111111011100101001111100101001010001110000000000000000000000000000000001101101101101101101101101101101101101101100000000000000000000000000000000111000111000");

                assertEquals("                                 _______\n" +
                                "                          _,,ad8888888888bba,_\n" +
                                "                       ,ad88888I888888888888888ba,\n" +
                                "                     ,88888888I88888888888888888888a,\n" +
                                "                   ,d888888888I8888888888888888888888b,\n" +
                                "                  d88888PP\"\"\"\" \"\"YY88888888888888888888b,\n" +
                                "                ,d88\"'__,,--------,,,,.;ZZZY8888888888888,\n" +
                                "               ,8IIl'\"                ;;l\"ZZZIII8888888888,\n" +
                                "              ,I88l;'                  ;lZZZZZ888III8888888,\n" +
                                "            ,II88Zl;.                  ;llZZZZZ888888I888888,\n" +
                                "           ,II888Zl;.                .;;;;;lllZZZ888888I8888b\n" +
                                "          ,II8888Z;;                 `;;;;;''llZZ8888888I8888,\n" +
                                "          II88888Z;'                        .;lZZZ8888888I888b\n" +
                                "          II88888Z; _,aaa,      .,aaaaa,__.l;llZZZ88888888I888\n" +
                                "          II88888IZZZZZZZZZ,  .ZZZZZZZZZZZZZZ;llZZ88888888I888,\n" +
                                "          II88888IZZ<'(@@>Z|  |ZZZ<'(@@>ZZZZ;;llZZ888888888I88I\n" +
                                "         ,II88888;   `\"\"\" ;|  |ZZ; `\"\"\"     ;;llZ8888888888I888\n" +
                                "         II888888l            `;;          .;llZZ8888888888I888,\n" +
                                "        ,II888888Z;           ;;;        .;;llZZZ8888888888I888I\n" +
                                "        III888888Zl;    ..,   `;;       ,;;lllZZZ88888888888I888\n" +
                                "        II88888888Z;;...;(_    _)      ,;;;llZZZZ88888888888I888,\n" +
                                "        II88888888Zl;;;;;' `--'Z;.   .,;;;;llZZZZ88888888888I888b\n" +
                                "        ]I888888888Z;;;;'   \";llllll;..;;;lllZZZZ88888888888I8888,\n" +
                                "        II888888888Zl.;;\"Y88bd888P\";;,..;lllZZZZZ88888888888I8888I\n" +
                                "        II8888888888Zl;.; `\"PPP\";;;,..;lllZZZZZZZ88888888888I88888\n" +
                                "        II888888888888Zl;;. `;;;l;;;;lllZZZZZZZZW88888888888I88888\n" +
                                "        `II8888888888888Zl;.    ,;;lllZZZZZZZZWMZ88888888888I88888\n" +
                                "         II8888888888888888ZbaalllZZZZZZZZZWWMZZZ8888888888I888888,\n" +
                                "         `II88888888888888888b\"WWZZZZZWWWMMZZZZZZI888888888I888888b\n" +
                                "          `II88888888888888888;ZZMMMMMMZZZZZZZZllI888888888I8888888\n" +
                                "           `II8888888888888888 `;lZZZZZZZZZZZlllll888888888I8888888,\n" +
                                "            II8888888888888888, `;lllZZZZllllll;;.Y88888888I8888888b,\n" +
                                "           ,II8888888888888888b   .;;lllllll;;;.;..88888888I88888888b,\n" +
                                "           II888888888888888PZI;.  .`;;;.;;;..; ...88888888I8888888888,\n" +
                                "           II888888888888PZ;;';;.   ;. .;.  .;. .. Y8888888I88888888888b,\n" +
                                "          ,II888888888PZ;;'                        `8888888I8888888888888b,\n" +
                                "          II888888888'                              888888I888888888888888b\n" +
                                "         ,II888888888                              ,888888I8888888888888888\n" +
                                "        ,d88888888888                              d888888I8888888888ZZZZZZ\n" +
                                "     ,ad888888888888I                              8888888I8888ZZZZZZZZZZZZ\n" +
                                "   ,d888888888888888'                              888888IZZZZZZZZZZZZZZZZZ\n" +
                                " ,d888888888888P'8P'                               Y888ZZZZZZZZZZZZZZZZZZZZ\n" +
                                ",8888888888888,  \"                                 ,ZZZZZZZZZZZZZZZZZZZZZZZ\n" +
                                "d88888888888888,                                ,ZZZZZZZZZZZZZZZZZZZZZZZZZZ\n" +
                                "88888888888888888a,      _                    ,ZZZZZZZZZZZZZZZZZZZZ88888888\n" +
                                "88888888888888888888ba,_d'                  ,ZZZZZZZZZZZZZZZZZ8888888888888\n" +
                                "888888888888888888888888888bbbaaa,,,______,ZZZZZZZZZZZZZZZ88888888888888888\n" +
                                "8888888888888888888888888888888888888888ZZZZZZZZZZZZZZZ88888888888888888888\n" +
                                "888888888888888888888888888888888888888ZZZZZZZZZZZZZZ8888888888888888888888\n" +
                                "88888888888888888888888888888888888888ZZZZZZZZZZZZZZ88888888888888888888888\n" +
                                "888888888888888888888888888888888888ZZZZZZZZZZZZZZ8888888888888888888888888\n" +
                                "8888888888888888888888888888888888ZZZZZZZZZZZZZZ888888888888888888888888888\n" +
                                "888888888888888888888888888888888ZZZZZZZZZZZZZZ8888888888888888 Normand  88\n" +
                                "8888888888888888888888888888888ZZZZZZZZZZZZZZ888888888888888888 Veilleux 88\n" +
                                "888888888888888888888888888888ZZZZZZZZZZZZZZ8888888888888888888888888888888\n" +
                                "\n",
                                output.toString());
        }

}