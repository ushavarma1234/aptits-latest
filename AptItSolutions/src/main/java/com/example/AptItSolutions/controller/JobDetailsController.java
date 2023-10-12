package com.example.AptItSolutions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AptItSolutions.Entity.JobDetails;
import com.example.AptItSolutions.service.JobDetailsService;

@RestController
@RequestMapping("/apijobdetails")
public class JobDetailsController {
	@Autowired
    private  JobDetailsService jobDetailsService;

   

	@PostMapping("/savejob")
	public String createJobDetails(@RequestParam("phoneNumber") String phoneNumber,
	                               @RequestParam("email") String email,
	                               @RequestParam("jobTitle") String jobTitle,
	                               @RequestParam("keySkills") String keySkills,
	                               @RequestParam("yearsOfExperience") String yearsOfExperience,
	                               @RequestParam("numberOfPositions") String numberOfPositions,
	                               @RequestParam("jobDescription") String jobDescription,
	                               @RequestParam("status")  String statusString) {

	    JobDetails jobDetails = new JobDetails();
	    
	    jobDetails.setPhoneNumber(phoneNumber);
	    jobDetails.setEmail(email);
	    jobDetails.setJobTitle(jobTitle);
	    jobDetails.setKeySkills(keySkills);
	    jobDetails.setYearsOfExperience(yearsOfExperience);
	    jobDetails.setNumberOfPositions(numberOfPositions);
	    jobDetails.setJobDescription(jobDescription);

	    // Convert the statusString to a Boolean
	    boolean status = Boolean.parseBoolean(statusString);
	    jobDetails.setStatus(status);

	    jobDetailsService.saveJobDetails(jobDetails);
	    
	    return "redirect:/adminDashboard";
	}



    @GetMapping("/alljobs")
    public ResponseEntity<List<JobDetails>> getAllJobDetails() {
        List<JobDetails> jobDetailsList = jobDetailsService.getAllJobDetails();
        return new ResponseEntity<>(jobDetailsList, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<JobDetails> getJobDetailsById(@PathVariable("id") Long id) {
        JobDetails jobDetails = jobDetailsService.getJobDetailsById(id);
        if (jobDetails != null) {
            return new ResponseEntity<>(jobDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    
    @PutMapping("/jobupdating/{id}")
    public JobDetails updateJobDetails(@PathVariable Long id,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("email") String email,
                                       @RequestParam("jobTitle") String jobTitle,
                                       @RequestParam("keySkills") String keySkills,
                                       @RequestParam("yearsOfExperience") String yearsOfExperience,
                                       @RequestParam("numberOfPositions") String numberOfPositions,
                                       @RequestParam("status") String statusString,
                                       @RequestParam("jobDescription") String jobDescription) {

        JobDetails updatedJobDetails = jobDetailsService.getJobDetailsById(id);

        updatedJobDetails.setPhoneNumber(phoneNumber);
        updatedJobDetails.setEmail(email);
        updatedJobDetails.setJobTitle(jobTitle);
        updatedJobDetails.setKeySkills(keySkills);
        updatedJobDetails.setYearsOfExperience(yearsOfExperience);
        updatedJobDetails.setNumberOfPositions(numberOfPositions);

        // Convert the statusString to a Boolean
        boolean status = Boolean.parseBoolean(statusString);
        updatedJobDetails.setStatus(status);

        updatedJobDetails.setJobDescription(jobDescription);

        return jobDetailsService.updateJobApplication(id, updatedJobDetails);
    }



    @DeleteMapping("/deletejob/{id}")
    public ResponseEntity<HttpStatus> deleteJobDetails(@PathVariable("id") Long id) {
        JobDetails jobDetails = jobDetailsService.getJobDetailsById(id);
        if (jobDetails != null) {
            jobDetailsService.deleteJobDetails(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}