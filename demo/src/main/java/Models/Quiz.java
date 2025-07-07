package Models;

public class Quiz {
    private long id;
    private String title;
    private String description;
    private String creator;
    private long submissions;
    private String createdAt;
    private boolean isRandomized;
    private boolean isMultiplePage;

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public long getSubmissions() { return submissions; }
    public void setSubmissions(long submissions) { this.submissions = submissions; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isRandomized() { return isRandomized; }
    public void setRandomized(boolean isRandomized) { this.isRandomized = isRandomized; }

    public boolean isMultiplePage() { return isMultiplePage; }
    public void setMultiplePage(boolean isMultiplePage) { this.isMultiplePage = isMultiplePage; }
}
