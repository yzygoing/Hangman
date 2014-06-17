package nl.mprog.apps.hangman5878241;

import java.io.IOException; 
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.provider.BaseColumns;
import android.util.Log; 
import android.content.ContentValues; 
import android.content.Context; 
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

public class WordsController extends SQLiteOpenHelper 
{
	// For debug purpose
	private static final String Test = null;
	
	// Variables for resources
	private final Context loadWords;
	private static Resources res;
	
	
	// Initialize Android SQLite DB 
	public WordsController(Context applicationcontext) 
	{ 
		super(applicationcontext, "androidsqlite.db", null, 1); 
		Log.d(Test,"Created");
		
		loadWords = applicationcontext;
		
		// Get resource file
		Resources res = loadWords.getResources();
	}
	
	// Create Words database
	@Override 
	public void onCreate(SQLiteDatabase database) 
	{ 
		String query; 
		query = "CREATE TABLE Wordlist ( WordId INTEGER PRIMARY KEY, Word TEXT, Size INT)"; 
		database.execSQL(query); 
		Log.d(Test,"Empty Wordlist table Created");
		
		// Initialize values for WordList
        ContentValues _Values = new ContentValues();  
		
		// Open small.xml file
		XmlResourceParser _xml = res.getXml(R.xml.wordstest);
		try
		{
			// Check for end of file
			int endOfDoc = _xml.getEventType();
			
			while (endOfDoc != XmlPullParser.END_DOCUMENT) 
			{	
				// Search for word tags (called "item" in small.xml)
				if ((endOfDoc == XmlPullParser.START_TAG) && (_xml.getName().equals("item")))
				{
					// If item is found get value of words and insert in SQlite WordList table
					String Word = _xml.getAttributeValue(null, WordListColumns.WORD);
					
					//int Size = _xml.getAttributeIntValue((Integer) null, WordListColumns.SIZE);
					
					// Put words in table WordList
					_Values.put(WordListColumns.WORD, Word);
					
					// _Values.put(WordListColumns.SIZE, Size);
					
					// Insert values in database
					database.insert(WordListColumns.TABLENAME, null, _Values);
				}
				endOfDoc = _xml.next();
			}
		}
		
		// Check for errors
        catch (XmlPullParserException e)
        {       
            Log.e(Test, e.getMessage(), e);
        }
        catch (IOException e)
        {
            Log.e(Test, e.getMessage(), e);
        }           
        finally
        {           
            // Close the xml file
            _xml.close();
        }
    }
			
	
	// For SQLiteOpenHelper
	@Override 
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) 
	{ 
		String query; 
		query = "DROP TABLE IF EXISTS Wordlist"; 
		database.execSQL(query); 
		onCreate(database); 
	}
	
	public static final class WordListColumns implements BaseColumns 
	{
	    private WordListColumns () {}
	    public static final String TABLENAME = "Wordlist";
	    public static final String WORD = "word";        
//	    public static final String SIZE;   
	}
	
}
