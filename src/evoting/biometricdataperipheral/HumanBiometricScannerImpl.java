package evoting.biometricdataperipheral;

import data.SingleBiometricData;
import exception.HumanBiometricScanningException;


public class HumanBiometricScannerImpl implements HumanBiometricScanner{
    private byte[] facialData = {5,15,25,35};
    private byte[] fingerData = {10,20,30,40};
    private boolean errorScanningFaceData = false;
    private boolean errorScanningFingerData = false;
    @Override
    public SingleBiometricData scanFaceBiometrics () throws HumanBiometricScanningException{
        if(!errorScanningFaceData){
            return new SingleBiometricData(facialData);

        }
        else {
            throw new HumanBiometricScanningException("Something went wrong while scanning face biometrics");
        }
    }

    @Override
    public SingleBiometricData scanFingerprintBiometrics () throws HumanBiometricScanningException{
        if(!errorScanningFingerData){
            return new SingleBiometricData(fingerData);

        }
        else {
            throw new HumanBiometricScanningException("Something went wrong while scanning finger biometrics");
        }
    }
}
