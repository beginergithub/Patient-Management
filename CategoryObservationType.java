public class CategoryObservationType extends ObservationType {
    private String[] categories;

    public CategoryObservationType(String code, String name, String[] categories) {
        super(code, name);
        this.categories = categories;
    }

    public String[] getCategories() {
        return categories;
    }

    @Override
    public String toString() {
        String result = "CategoryObservationType[code: " + getCode() + ", name: " + getName() + ", categories: |";
        for (int i = 0; i < categories.length; i++) {
            result += categories[i];
            if (i < categories.length) {
                result += "|";
            }
        }
        result += "]\n";
        return result;
    }
}