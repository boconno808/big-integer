public class Factorial{

    private int number;
    private BigInteger factorial;

    public Factorial (int number){
        this.number = number;
        this.factorial = new BigInteger("1");
    }

    public String determineFactorial(){
        if (this.number == 0){
            BigInteger zeroException = new BigInteger("1");
            return zeroException.toString();
        }
        for (int i = 1; i <= this.number; i++){
            this.factorial = this.factorial.product(new BigInteger(String.valueOf(i)));
        }
        return this.factorial.toString();
    }

    public static void main(String args[]){
        try {
            if (args.length != 1) {
                System.out.println("Please input one argument only!");
            } else {
                int givenValue = Integer.parseInt(args[0]);
                if (givenValue < 0){
                    System.out.println("Please input a positive value");
                } else {
                    Factorial testFactorial = new Factorial(givenValue);
                    System.out.println(testFactorial.determineFactorial());
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Wrong Format! Please input integers only!");
        }
    }
}
