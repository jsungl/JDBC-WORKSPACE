package member.model.exception;

/**
 * checkedException : extends Exception 예외처리강제화
 * 
 * uncheckedException : extends RuntimeException 예외처리 수월
 *
 */
public class MemberException extends RuntimeException {

	public MemberException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	//cause : 애초에 발생한 예외
	public MemberException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MemberException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
