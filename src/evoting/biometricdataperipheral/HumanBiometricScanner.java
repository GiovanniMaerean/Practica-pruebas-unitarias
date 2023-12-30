package evoting.biometricdataperipheral;

import data.SingleBiometricData;
import exception.HumanBiometricScanningException;

public interface HumanBiometricScanner {
    SingleBiometricData scanFaceBiometrics () throws HumanBiometricScanningException;
    SingleBiometricData scanFingerprintBiometrics () throws HumanBiometricScanningException;
}
