public class Fibbonacci {

    private BigInteger bigIntegerOne;
    private BigInteger bigIntegerTwo;
    private BigInteger bigIntegerThree;
    private int count;

    public Fibbonacci (int count) {
        this.count = count;
        this.bigIntegerOne = new BigInteger("0");
        this.bigIntegerTwo = new BigInteger("1");
        this.bigIntegerThree = new BigInteger("0");
    }

    public String fibbonacciValueAtIndex(){
        if (this.count == 1){
            BigInteger fibbonacciAnswer = new BigInteger("0");
            return fibbonacciAnswer.toString();
        }
        if (this.count == 2) {
            BigInteger fibbonacciAnswer = new BigInteger("1");
            return fibbonacciAnswer.toString();
        }
        for (int i = 2; i < this.count; i++){
            this.bigIntegerThree = this.bigIntegerOne.sum(this.bigIntegerTwo);
            this.bigIntegerOne = this.bigIntegerTwo;
            this.bigIntegerTwo = this.bigIntegerThree;
        }
        return this.bigIntegerThree.toString();
    }

    public static void main(String args[]){
        try {
            if (args.length != 1) {
                System.out.println("Please input one argument only!");
            } else {
                int givenValue = Integer.parseInt(args[0]);
                if (givenValue <= 0){
                    System.out.println("Please input a value greater than 0");
                } else {
                    Fibbonacci testFibbonacci = new Fibbonacci(givenValue);
                    System.out.println(testFibbonacci.fibbonacciValueAtIndex());
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong Format! Please input integers only!");
        }
    }
}
