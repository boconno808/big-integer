public class BigInteger {

    private int[] digitArray;
    private boolean isNegative;
    public static final BigInteger ZERO = new BigInteger("0"); // a classwide constant for zero
    public static final BigInteger ONE = new BigInteger("1"); // a classwide constant for one
    public static final BigInteger TEN = new BigInteger("10"); // a classwide constant for ten

    public BigInteger (String val) {
        if (val.length() == 0){
            throw new IllegalArgumentException();
        }
        this.isNegative = false;
        String trimmedVal = val.trim();
        if (trimmedVal.charAt(0) == '+'){
            trimmedVal = trimmedVal.substring(1);
        }
        if (trimmedVal.charAt(0) == '-'){
            this.isNegative = true;
            trimmedVal = trimmedVal.substring(1);
        }
        int untouchedValLength = trimmedVal.length();
        this.digitArray = new int[untouchedValLength];
        for (int i = 0; i < untouchedValLength ; i++){
            char currentChar = trimmedVal.charAt(trimmedVal.length() - 1);
            if (Character.isLetter(currentChar)){
                throw new IllegalArgumentException();
            } else {
                this.digitArray[i] = Character.getNumericValue(currentChar);
                trimmedVal = trimmedVal.substring(0,trimmedVal.length() -1);
            }
        }
        this.removeLeadingZeroes();
    }

    public boolean getIsNegative(){
        return this.isNegative;
    }

    public void setIsNegative(boolean newValue){
        this.isNegative = newValue;
    }

    public void absoluteValue(){
        this.isNegative = false;
    }

    public int[] getDigitArray(){
        return this.digitArray;
    }

    /**
     * removes zeroes from the digit array
     **/
    public void removeLeadingZeroes(){
        int i = this.digitArray.length;
        while (i > 1 && this.digitArray[i - 1] == 0){
            i = i -1;
        }
        int lengthOfNewDigits = i;
        int[] newDigits = new int[lengthOfNewDigits];
        for (int j = 0; j < newDigits.length; j++){
            newDigits[j] = this.digitArray[j];
        }
        this.digitArray = newDigits;
    }

    /**
     * adds zeroes to the digit array until it reaches the desired length
     **/
    public void addLeadingZeroes(int desiredLength){
        int originalLength = this.digitArray.length;
        int[] newDigits = new int[desiredLength];
        for (int j = 0; j < newDigits.length; j++){
            if (j < originalLength){
                newDigits[j] = this.digitArray[j];
            } else {
                newDigits[j] = 0;
            }
        }
        this.digitArray = newDigits;
    }

    public BigInteger sum (BigInteger val) {
        if (!isNegative && val.getIsNegative()){
            val.setIsNegative(false);
            return this.difference(val);
        } else if (isNegative && !val.getIsNegative()){
            val.setIsNegative(true);
            return this.difference(val);
        }
        int resultLength = 0;
        if (digitArray.length == val.getDigitArray().length || digitArray.length > val.getDigitArray().length){
            resultLength = this.digitArray.length + 1;
        } else if (digitArray.length < val.getDigitArray().length){
            resultLength = val.getDigitArray().length + 1;
        }
        int[] result = new int[resultLength];
        this.addLeadingZeroes(result.length);
        val.addLeadingZeroes(result.length);
        if ((!isNegative && !val.getIsNegative()) || (isNegative && val.getIsNegative())){
            int carry = 0;
            int sum = 0;
            int answer = 0;
            for (int i = 0; i < result.length; i++){
                sum = carry + this.digitArray[i] + val.getDigitArray()[i];
                answer = sum % 10;
                result[i] = answer;
                carry = sum / 10;
            }
        }
        this.digitArray = result;
        this.isNegative = isNegative && val.getIsNegative() ? true : false;
        String resultString = this.toString();
        BigInteger sumBigInt = new BigInteger(resultString);
        return sumBigInt;
    }

    public BigInteger difference (BigInteger val) {
        if (!isNegative && val.getIsNegative()){
            val.setIsNegative(false);
            return this.sum(val);
        } else if (isNegative && !val.getIsNegative()){
            val.setIsNegative(true);
            return this.sum(val);
        }
        int resultLength = 0;
        boolean valIsSmaller = true;
        boolean bothNegative = false;
        boolean switched = false;
        bothNegative = isNegative && val.getIsNegative() ? true: false;
        this.absoluteValue();
        val.absoluteValue();
        if (this.compareWith(val) == 0){
            BigInteger differenceBigIntEqual = new BigInteger("0");
            return differenceBigIntEqual;
        } else if( this.compareWith(val) == 1){
            valIsSmaller = true;
        } else if (this.compareWith(val) == -1){
            valIsSmaller = false;
        }
        if (digitArray.length == val.getDigitArray().length || digitArray.length > val.getDigitArray().length){
            resultLength = this.digitArray.length;
        } else if (digitArray.length < val.getDigitArray().length){
            resultLength = val.getDigitArray().length;
        }
        int[] result = new int[resultLength];
        this.addLeadingZeroes(result.length);
        val.addLeadingZeroes(result.length);
        int[] topArray;
        int[] bottomArray;
        if (valIsSmaller) {
            topArray = this.digitArray;
            bottomArray = val.getDigitArray();
        } else {
            switched = true;
            topArray = val.getDigitArray();
            bottomArray = this.digitArray;
        }
        for (int currentPos = 0; currentPos < result.length; currentPos++){
            result[currentPos] = topArray[currentPos] - bottomArray[currentPos];
            if (result[currentPos] < 0){
                result[currentPos] += 10;
                topArray[currentPos + 1] -= 1;
            }
        }
        this.digitArray = result;
        if ((bothNegative && switched) || (!bothNegative && !switched)) {
            this.isNegative = false;
        } else if ((bothNegative && !switched) || (!bothNegative && switched)){
            this.isNegative = true;
        }
        String resultString = this.toString();
        BigInteger differenceBigInt = new BigInteger(resultString);
        return differenceBigInt;
    }

    /**
     * halves the digitArray
     **/
    public void halve(){
        int carry = 0;
        for (int i = this.digitArray.length - 1; i > -1; i--){
            int originalValue = this.digitArray[i];
            this.digitArray[i] = this.digitArray[i] / 2;
            this.digitArray[i] = this.digitArray[i] + carry;
            if (originalValue % 2 == 0){
                carry = 0;
            } else {
                carry = 5;
            }
        }
        this.removeLeadingZeroes();
    }

    /**
     * doubles the digitArray
     **/
    public void twiceAsMuch(){
        int[] newDigitArray = this.sum(this).getDigitArray();
        this.digitArray = newDigitArray;
    }


    public BigInteger product (BigInteger val) {
        boolean originalNegative = false;
        if (this.isNegative && val.getIsNegative() || !this.isNegative && !val.getIsNegative()){
            originalNegative = false;
        } else if (this.isNegative && !val.getIsNegative() || !this.isNegative && val.getIsNegative()){
            originalNegative = true;
        }
        if (this.equals(new BigInteger ("0")) || val.equals(new BigInteger ("0"))){
            BigInteger productBigInt = new BigInteger("0");
            return productBigInt;
        }
        this.absoluteValue();
        val.absoluteValue();
        String runningTotal = "";
        while(!val.equals(new BigInteger ("0"))){
            if (val.getDigitArray()[0] % 2 == 0){
                val.halve();
                this.twiceAsMuch();
            } else if (val.getDigitArray()[0] % 2 != 0){
                runningTotal = runningTotal + "," + this.toString();
                val.halve();
                this.twiceAsMuch();
            }
        }
        runningTotal = runningTotal.substring(1); // to delete the first comma
        BigInteger total = new BigInteger("0");
        String[] totalStringArray = runningTotal.split(",");
        for (int i = 0; i < totalStringArray.length; i++){
            total = total.sum(new BigInteger(totalStringArray[i]));
        }
        int[] resultDigitArray = total.getDigitArray();
        this.digitArray = resultDigitArray;
        this.isNegative = originalNegative;
        String resultString = this.toString();
        BigInteger productBigInt = new BigInteger(resultString);
        return productBigInt;

    }

    public BigInteger quotient (BigInteger val) {
        boolean originalNegative = false;
        if (this.isNegative && val.getIsNegative() || !this.isNegative && !val.getIsNegative()){
            originalNegative = false;
        } else if (this.isNegative && !val.getIsNegative() || !this.isNegative && val.getIsNegative()){
            originalNegative = true;
        }
        this.absoluteValue();
        val.absoluteValue();
        if (val.equals(new BigInteger("0"))){
            throw new ArithmeticException();
        }
        if (this.equals(new BigInteger("0")) || this.compareWith(val) == -1){
            BigInteger quotientBigInt = new BigInteger("0");
            return quotientBigInt;
        }
        int index = this.digitArray.length-1;
        BigInteger smallDividend = new BigInteger(String.valueOf(this.digitArray[index]));
        String resultString = "";
        if (index == 0){
            resultString = String.valueOf(this.digitArray[0] / val.getDigitArray()[0]);
        }
        while (index >= 1){
            int dropADigit = 0;
            while ((new BigInteger(val.toString()).compareWith(smallDividend) == 1) && (index >=1)){
                index -= 1;
                smallDividend = (smallDividend.product(new BigInteger ("10"))).sum(new BigInteger
                        (String.valueOf(this.digitArray[index])));
                if (dropADigit >=1){
                    resultString = resultString + "0";
                }
                dropADigit ++;
            }
            int answer = 1;
            BigInteger answerValAddition = new BigInteger(val.toString());
            do {
                answer++;
                answerValAddition = answerValAddition.sum(val);
            } while ((smallDividend.compareWith(answerValAddition) == 1) ||
                    (smallDividend.compareWith(answerValAddition) == 0));
            answer = answer - 1;
            if (new BigInteger(val.toString()).compareWith(smallDividend) == 1){
                answer = 0;
            }
            BigInteger answerValProduct = new BigInteger(val.toString()).
                    product(new BigInteger(String.valueOf(answer)));
            resultString += String.valueOf(answer);
            smallDividend = smallDividend.difference(answerValProduct);
        }
        BigInteger quotientBigInt = new BigInteger(resultString);
        quotientBigInt.setIsNegative(originalNegative);
        return quotientBigInt;
    }

    public BigInteger remainder (BigInteger val) {
        if (val.equals(new BigInteger("0"))){
            throw new ArithmeticException();
        }
        BigInteger remainderBigInt = (new BigInteger(this.toString())).difference((new BigInteger(this.toString()).
                quotient(new BigInteger(val.toString()))).product(new BigInteger(val.toString())));
        return remainderBigInt;
    }

    public String toString () {
        String digitString = "";
        for (int i = 0; i < this.digitArray.length; i++){
            digitString = this.digitArray[i] + digitString;
        }
        if (this.isNegative) {
            digitString = "-" + digitString;
        }
        return digitString;
    }

    /**
     * Returns 1 if arrayOne is greater than arrayTwo, -1 if its the reverse
     * returns 0 if both are identical, only works if both arrays are equal lengths
     **/
    public int greaterArray(int[] arrayOne, int[] arrayTwo){
        int answer = 0;
        if (arrayOne.length == arrayTwo.length){
            for (int i =0; i < arrayOne.length; i ++){
                if (arrayOne[i] > arrayTwo[i]){
                    answer = 1;
                } else if (arrayOne[i] < arrayTwo[i]) {
                    answer = -1;
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return answer;
    }

    public int compareWith (BigInteger val) {
        int answer = 1;
        if (this.equals(val)) {
            answer = 0;
        }
        boolean valIsPositive = !val.getIsNegative();
        boolean thisIsPositive = !this.isNegative;
        if (thisIsPositive && valIsPositive) {
                if (this.digitArray.length > val.getDigitArray().length){
                    answer = 1;
                } else if (this.digitArray.length < val.getDigitArray().length) {
                    answer = -1;
                } else {
                    answer = greaterArray(this.digitArray, val.getDigitArray());
                }
        } else if (!thisIsPositive && !valIsPositive) {
                if (this.digitArray.length > val.getDigitArray().length){
                    answer = -1;
                } else if (this.digitArray.length < val.getDigitArray().length) {
                    answer = 1;
                } else {
                    answer = greaterArray(this.digitArray, val.getDigitArray()) * -1;
                }
        }  else if (thisIsPositive && !valIsPositive) {
            answer = 1;
        } else if (!thisIsPositive && valIsPositive) {
            answer = -1;
        }
        return answer;
    }

    public boolean equals (Object x) {
        if (x == null) {
            return false;
        }
        if (getClass() != x.getClass()) {
            return false;
        }
        if (this == x) {
            return true;
        }
        BigInteger insertedBigInt = (BigInteger)x;
        return toString().equals(insertedBigInt.toString());
    }

    public static BigInteger valueOf (long val){
        String strVal = Long.toString(val);
        BigInteger longBigInt = new BigInteger(strVal);
        return longBigInt;
    }
}
