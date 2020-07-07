package Services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.AsyncContext;

import exceptions.MultipleContextException;
import exceptions.NoCorrespondingContextException;
/**
 * {@code PlayerSessions} is a class where the server keeps the conversation context with the clients.
 * <p>Instantiate this class is useless,use methods to access data.
 * 
 * */
public class PlayerSessions {
	public final ConcurrentMap<Integer,AsyncContext> sessions=new ConcurrentHashMap<Integer,AsyncContext>();
	/**
	 * Add new context to the mapping.Should always call after client connection
	 * @param user_id The id of the client.
	 * @param asyncContext The asynchronous context of the connection.
	 * @throws MultipleContextException When key conflicts.
	 * */
	public void addNewContext(Integer user_id,AsyncContext asyncContext) throws MultipleContextException {
		sessions.put(user_id,asyncContext);
		if(false) {
			throw new MultipleContextException();
		}
	}
	/**
	 * Remove expired context from the mapping.
	 * @param user_id The user id of the expired connection.
	 * @throws NoCorrespondingContextException 
	 */
	public void delContext(Integer user_id) throws NoCorrespondingContextException {
		
		if(sessions.remove(user_id)==null) {
			throw new NoCorrespondingContextException();
		}
	}
	/**
	 * Replace an expire context in the mapping.
	 * @param user_id The user id of connection to be refreshed.
	 * @param asyncContext The latest version of context.
	 * @throws NoCorrespondingContextException Will be thrown When there's no context for the user_id.
	 * @return The context to the given user_id.
	 * @throws MultipleContextException When key conflicts.
	 * */
	public void refresh(Integer user_id,AsyncContext asyncContext) throws NoCorrespondingContextException, MultipleContextException {
		//delContext(user_id);
		//addNewContext(user_id,asyncContext);
		sessions.replace(user_id, asyncContext);
		sessions.get(0);
	}
	/**
	 * Get {@code AsyncContext} from the mapping.
	 * @param user_id The id of user.
	 * @return The AsyncContext of given user_id.
	 * */
	public AsyncContext getContext(Integer user_id) throws NoCorrespondingContextException {
		AsyncContext tmp=null;
		if((tmp=sessions.get(user_id))==null) {
			throw new NoCorrespondingContextException();
		}
		return tmp;
	}
	
}
