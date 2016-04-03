
package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	CANTalon talon = new CANTalon(1);
	Joystick joystickLeft = new Joystick(0);
	Joystick joystickRight = new Joystick(1);
	Joystick joystickOp = new Joystick(2);
	
	double kP, kI, kD;
	int setpoint = 0;

	PIDController pidController = new PIDController(0, 0, 0, talon, talon);
	
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Position~", talon.getEncPosition());
		
		kP = 0.001;
		kI = 0;
		kD = 0;
		// 0.001-0-0 works well
		
//		kP = (((joystickLeft.getZ() * -1) + 1) / 2.0) * 0.01;
//		kI = (((joystickRight.getZ() * -1) + 1) / 2.0) * 0.01;
//		kD = (((joystickOp.getThrottle() * -1) + 1) / 2.0) * 0.01;
		
		SmartDashboard.putNumber("P~", kP);
		SmartDashboard.putNumber("I~", kI);
		SmartDashboard.putNumber("D~", kD);
		
		if(joystickOp.getPOV() == 0) {
			setpoint += 50;
		} else if(joystickOp.getPOV() == 180) {
			setpoint -= 50;
		}
		
		SmartDashboard.putNumber("Setpoint~", setpoint);

		if(joystickOp.getRawButton(1)) {
			pid();
		} else if(joystickOp.getRawButton(2)) {
			pidController.disable();
			talon.set(-joystickOp.getY());
		} else {
			pidController.disable();
			talon.set(0);
		}
		
		
	}
	
	public void pid() {
		pidController.setSetpoint(setpoint);
		SmartDashboard.putNumber("Error~", pidController.getError());

		pidController.setPID(kP, kI, kD);
		pidController.enable();
	}

}
