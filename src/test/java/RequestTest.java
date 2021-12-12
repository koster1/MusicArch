import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jcg.hibernate.maven.RemoteDAO;
import com.jcg.hibernate.maven.UserRequests;

public class RequestTest {
	String rTitle = "title";
	String rContent = "Content ";
	private RemoteDAO rDAO = RemoteDAO.getInstance();
	
	@Test
	@DisplayName("Create a new request")
	public void createRequest() {
		UserRequests uRequest = new UserRequests();
		uRequest.setRequestTitle(rTitle);
		uRequest.setRequestContents(rContent);
		
		try {
			rDAO.createRequest(uRequest);
		} catch (Exception e) {
			System.out.println("Shit went borked");
			e.printStackTrace();
		}
		try {
			int foundID = rDAO.searchRequestTitle(rTitle).getRequestID();
			assertEquals(rTitle, rDAO.searchRequestTitle(rTitle).getRequestTitle());
			rDAO.removeRequest(foundID);
		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		
	}

}
