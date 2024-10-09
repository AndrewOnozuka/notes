
class LongWordChooser implements MyChooser<String> {

	@Override
	public boolean chooseElement(String s) {
		return s.length() > 5;
	}

} 

// Add your choosers here

class HasAChooser implements MyChooser<String> {
	@Override
	public boolean chooseElement(String s)
	{
		return s.contains("a");
	}
}

class HasBChooser implements MyChooser<String> {
	@Override
	public boolean chooseElement(String s)
	{
		return s.contains("b");
	}
}