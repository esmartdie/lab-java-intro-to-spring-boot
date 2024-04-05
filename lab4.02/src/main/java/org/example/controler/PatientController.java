package org.example.controler;

import org.example.model.Patient;
import org.example.model.Status;
import org.example.repository.IEmployeeRepository;
import org.example.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private IPatientRepository patientRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;

    @GetMapping("/patients")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/patients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Patient getPatientById(@PathVariable(name="id") Integer patientID) {
        return patientRepository.findById(patientID).get();
    }
    @GetMapping("/patients/date_of_birth_range")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> getByBirthdateRange(@RequestParam String startDate, @RequestParam String endDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = null;
        Date parsedEndDate = null;
        try {
            parsedStartDate = dateFormat.parse(startDate);
            parsedEndDate = dateFormat.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientRepository.findByDateOfBirthBetween(parsedStartDate, parsedEndDate);
    }

    @GetMapping("/patients/department/{department}")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> getPatientsByAdmittingDoctorDepartment(@PathVariable(name="department") String department) {
        return patientRepository.findPatientsByEmployeeDepartment(department).get();
    }

    @GetMapping("/patients/status/off")
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> getPatientsByStatusOff() {
        return patientRepository.findPatientsByEmployeeStatusOff().get();
    }
}
