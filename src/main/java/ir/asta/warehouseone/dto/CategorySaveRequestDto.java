package ir.asta.warehouseone.dto;

public class CategorySaveRequestDto {

    private String subject;

    public CategorySaveRequestDto(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
