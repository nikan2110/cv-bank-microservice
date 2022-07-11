package telran.cvbank.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import telran.cvbank.dto.CVDto;
import telran.cvbank.dto.CVSearchDto;
import telran.cvbank.dto.NewCVDto;
import telran.cvbank.service.interfaces.CVService;

@RestController

@RequestMapping("/cvbank/cv")
@CrossOrigin(origins = "*",
        methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT},
        allowedHeaders = "*", exposedHeaders = "*")
public class CVController {

    CVService cvService;

    @Autowired
    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @PostMapping
    public CVDto addCV(@RequestBody NewCVDto newCV, @RequestHeader(name = "id") String id) {
        return cvService.addCV(newCV, id);
    }

    @PostMapping("/cvs")
    public List<CVDto> getCVsByIDs(@RequestBody Collection<String> cvsId) {
        return cvService.getCVs(cvsId);

    }

    @PostMapping("/cvs/aggregate")
    public List<CVDto> getCVsByParameters(@RequestBody CVSearchDto paramaters) {
        return cvService.getCVsByParamaters(paramaters);
    }

    @GetMapping("/{cvId}")
    public CVDto getCV(@PathVariable String cvId, @RequestHeader(name = "role") String role) {
        return cvService.getCV(cvId, role);
    }

    @GetMapping("/cvs/published")
    public List<CVDto> getPublishedCVs() {
        return cvService.getPublishedCVs();
    }

    @GetMapping("/cvs/publishExpired")
    public List<CVDto> getPublishExpired() {
        return cvService.getPublishedCVsDateExpired();
    }

    @PutMapping("/anonymizer/{cvId}")
    public CVDto anonymiseCV(@PathVariable String cvId, @RequestBody Set<String> anonymousFields) {
        return cvService.anonymiseCV(cvId, anonymousFields);
    }

    @PutMapping("/{cvId}")
    public CVDto updateCV(@PathVariable String cvId, @RequestBody NewCVDto newDataCV) {
        return cvService.updateCV(cvId, newDataCV);
    }

    @PutMapping("/publish/{cvId}")
    public CVDto publishCV(@PathVariable String cvId) {
        return cvService.publishCV(cvId);
    }

    @DeleteMapping("/{cvId}")
    public void removeCV(@PathVariable String cvId, @RequestHeader("id") String id) {
        cvService.removeCV(cvId, id);
    }

}
