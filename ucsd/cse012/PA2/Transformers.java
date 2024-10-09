
class UpperCaseTransformer implements MyTransformer<String> {

	public String transformElement(String s) {
		return s.toUpperCase();
	}
}

// Add your transformers here

class ReplaceAWithB implements MyTransformer<String> {
	
	@Override
	public String transformElement(String s)
	{
		return s.replaceAll("a", "b");
	}
}

class ReplaceBWithC implements MyTransformer<String> {
	
	@Override
	public String transformElement(String s)
	{
		return s.replaceAll("b", "c");
	}
}