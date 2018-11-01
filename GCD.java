public class GCD {

    private BigInteger bigIntegerOne;
    private BigInteger bigIntegerTwo;

    public GCD (String bigIntOne, String bigIntTwo) {
        this.bigIntegerOne = new BigInteger(bigIntOne);
        this.bigIntegerTwo = new BigInteger(bigIntTwo);
    }

    public String calculateGCD(){
        this.bigIntegerOne.absoluteValue();
        this.bigIntegerTwo.absoluteValue();
        while (!this.bigIntegerTwo.equals(new BigInteger("0"))){
            BigInteger temporaryBigInt = new BigInteger(this.bigIntegerOne.toString());
            this.bigIntegerOne = this.bigIntegerTwo;
            this.bigIntegerTwo = temporaryBigInt.remainder(this.bigIntegerTwo);
        }
        return this.bigIntegerOne.toString();
    }

    public static void main(String args[]){
        try {
            if (args.length != 2) {
                System.out.println("Please input two argument only!");
            } else {
                GCD testGCD = new GCD(args[0],args[1]);
                System.out.println(testGCD.calculateGCD());
                java.math.BigInteger expected = new java.math.BigInteger(args[0])
                        .gcd(new java.math.BigInteger(args[1]));
                System.out.println("gcd check: " + expected);
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong Format! Please input string representation of BigInteger only!");
        }
    }
}
