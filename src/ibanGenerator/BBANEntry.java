package ibanGenerator;

import org.apache.commons.lang3.RandomStringUtils;

public class BBANEntry {
    // The Length of BBAN Entry.
    private final Integer length;
    // Entry Type - N, A, C
    // C = Mixed-Case Alpha-numeric String.
    // N = Numeric String.
    // A = Upper-Case String.
    private final BBANEntryType bbanEntryType;

    /***
     * Constructor -
     *
     * @param length        Length of Characters
     * @param bbanEntryType Character Type.
     */
    public BBANEntry(Integer length, BBANEntryType bbanEntryType) {
        this.bbanEntryType = bbanEntryType;
        this.length = length;
    }

    /***
     * Generate Random String for this Given Entry Type.
     *
     * @return Random String for given length and type.
     */
    public String getRandomString() {
        if (bbanEntryType == BBANEntryType.C) {
            return RandomStringUtils.randomAlphanumeric(length);
        } else if (bbanEntryType == BBANEntryType.A) {
            return RandomStringUtils.random(length, (int) 'A', (int) 'Z' + 1, true, false);
        } else if (bbanEntryType == BBANEntryType.N) {
            return RandomStringUtils.randomNumeric(length);
        }

        return "";
    }
}