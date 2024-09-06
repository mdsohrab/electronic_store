package Electronic.Store.Electronic.Store.Dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder //for obejct create use this
public class PageableResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalNumber;
    private int totalPages;
    private boolean lastPage;

}
