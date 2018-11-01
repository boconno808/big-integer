public class PrintBigInt {

    private BigInteger bigInt;

    public PrintBigInt(String value) {
        this.bigInt = new BigInteger(value);
    }

    public String PrintBigIntToString(){
        return this.bigInt.toString();
    }

    public static void main (String[] args){
        PrintBigInt testPrintBigInt = new PrintBigInt(args[0]);
        System.out.println(testPrintBigInt.PrintBigIntToString());
    }
}
