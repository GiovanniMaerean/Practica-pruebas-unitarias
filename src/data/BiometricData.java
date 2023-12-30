package data;

final public class BiometricData {
    SingleBiometricData facialData;
    SingleBiometricData fingerprintData;

    public BiometricData(SingleBiometricData facialData, SingleBiometricData fingerprintData) throws NullPointerException{
        if (facialData == null || fingerprintData == null) {
            throw new NullPointerException("Facial data and fingerprint data cannot be null");
        }

        this.facialData = facialData;

        this.fingerprintData = fingerprintData;
    }


    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiometricData biometricData = (BiometricData) o;
        return facialData.equals(biometricData.facialData) && fingerprintData.equals(biometricData.fingerprintData);
    }
}
