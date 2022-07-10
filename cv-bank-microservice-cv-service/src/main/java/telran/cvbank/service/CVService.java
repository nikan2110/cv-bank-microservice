package telran.cvbank.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import telran.cvbank.dto.CVDto;
import telran.cvbank.dto.CVSearchDto;
import telran.cvbank.dto.NewCVDto;

public interface CVService {

    CVDto addCV(NewCVDto newCV, String login);

    CVDto getCV(String cvId, String role);

    List<CVDto> getCVs(Collection<String> cvsId);

    List<CVDto> getPublishedCVs();

    List<CVDto> getPublishedCVsDateExpired();

    CVDto updateCV(String cvId, NewCVDto newDataCV);

    CVDto publishCV(String cvId);

    void removeCV(String cvId, String login);

    CVDto anonymiseCV(String cvId, Set<String> anonymousFields);

    List<CVDto> getCVsByParamaters(CVSearchDto paramaters);
}
