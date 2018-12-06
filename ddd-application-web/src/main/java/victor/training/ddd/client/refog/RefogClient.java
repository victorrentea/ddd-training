package victor.training.ddd.client.refog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import victor.training.ddd.ReglissException;
import victor.training.ddd.ReglissException.ErrorCode;
import victor.training.ddd.client.refog.jaxb.Field;
import victor.training.ddd.client.refog.jaxb.RefogObject;
import victor.training.ddd.client.refog.jaxb.RefogSearch;
import victor.training.ddd.domain.model.User;

@Service
public class RefogClient {

	@Value("${refog.host}")
	private String refogHost;

	@Value("${refog.user}")
	private String refogUser;

	@Value("${refog.pass}")
	private String refogPass;
	
    private static final JAXBContext JAXB_CONTEXT;
    
    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(RefogSearch.class);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
	public User getExactlyOne(String username) {
		List<RefogUser> refogUsers = searchUsers(username);
		
		if (refogUsers.isEmpty()) {
			throw new ReglissException(ErrorCode.SEARCH_REFOG_USER_NOT_FOUND);
		}
		return toUserEntity(refogUsers.get(0));
	}
	
	private List<RefogUser> searchUsers(String username) {
		RefogSearch result = doSearch(username);

		if (result == null || result.getData() == null) {
			return null;
		}
		List<RefogUser> refogUsers = new ArrayList<RefogUser>();
		for (RefogObject refogObject : result.getData().getRefogObject()) {
			RefogUser refogUser = new RefogUser();
			for (Field field : refogObject.getFields().getField()) {
				if (field.getName().equals("SIP_PRENOM_USUEL")) {
					refogUser.setFirstName(field.getValue());
				}
				if (field.getName().equals("SIP_NOM_USUEL")) {
					refogUser.setLastName(field.getValue());
				}
				if (field.getName().equals("SIP_I_UID")) {
					refogUser.setUsername(field.getValue());
				}
				if (field.getName().equals("SIP_EMAIL_GROUPE")) {
					refogUser.setEmail(field.getValue());
				}
				if (field.getName().equals("SIP_UO_POLE_EN")) {
					refogUser.setPole(field.getValue());
				}
				if (field.getName().equals("SIP_UO_PRINC_EN")) {
					refogUser.setEntity(field.getValue());
				}
				if (field.getName().equals("SIP_PAYS_EN")) {
					refogUser.setCountryName(field.getValue());
				}
				if (field.getName().equals("SIP_METIER_EN")) {
					refogUser.setBusinessLine(field.getValue());
				}
			}
			refogUsers.add(refogUser);
		}
		return refogUsers;
	}
	
	private RefogSearch doSearch(String username) {
		RefogSearch result = null;
		try {
			String inputFields = "";
			if (StringUtils.isNotBlank(username) && username.length() > 2) {
				inputFields = inputFields + username;
			}
			String url = computeSearchUrl(username, inputFields);
			Unmarshaller unmarshaller = JAXB_CONTEXT.createUnmarshaller();
			result = (RefogSearch) unmarshaller.unmarshal(new URL(url));
		} catch (IOException e) {
			throw new RefogException(e);
		} catch (JAXBException e) {
			throw new RefogException(e);
		} catch (RuntimeException e) {
			throw new RefogException(e);
		}
		return result;
	}

	private String computeSearchUrl(String username, String inputFields)
			throws IOException {
		return this.refogHost + "/RefogWS?" + //
				"applicationId=" + refogUser + //
				"&applicationPwd=" + refogPass + //
				"&searchType=RSI" + //
				"&searchText=" + inputFields + //
				"&objectType=1" + //
				"&outputFields=SIP_I_UID,SIP_NOM_USUEL,SIP_PRENOM_USUEL,SIP_EMAIL_GROUPE,SIP_UO_POLE_EN,SIP_UO_PRINC_EN,SIP_PAYS_EN,SIP_METIER_EN";
							   
	}
	
	private User toUserEntity(RefogUser refogUser) {
		User newUser = new User();
		newUser.setFirstName(refogUser.getFirstName());
		newUser.setLastName(refogUser.getLastName());
		newUser.setUsername(refogUser.getUsername());
		newUser.setBusinessLine(refogUser.getBusinessLine());
//		newUser.setCountry(getCountryFromCountryName(refogUser.getCountryName()));
		newUser.setEmail(refogUser.getEmail());
		//newUser.setLanguage(null);
		return newUser;
	}
	
    
	

	

}
