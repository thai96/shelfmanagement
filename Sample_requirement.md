Đây là một bản "Yêu cầu dự án (Project Brief / Request for Proposal)" được mô
phỏng giống hệt như một tài liệu từ khách hàng doanh nghiệp gửi cho team phát
triển/kiến trúc sư phần mềm.

Văn bản này được viết theo ngôn ngữ của Business (Nghiệp vụ). Nhiệm vụ của bạn
(với tư cách là Backend/Solutions Architect) là đọc hiểu, phân tích và chuyển
đổi nó thành các quyết định về Kiến trúc (Architecture), Database, và Tech
Stack.

TÀI LIỆU YÊU CẦU DỰ ÁN: HỆ THỐNG QUẢN LÝ TỒN KHO OMNI-CHANNEL (DỰ ÁN: OMNISTOCK)

1. Bối cảnh dự án (Project Background)

Chúng tôi là TechWear VN, một chuỗi bán lẻ thời trang và phụ kiện. Hiện tại
chúng tôi có 1 Kho tổng (Central Warehouse) tại Hà Nội, 20 Cửa hàng bán lẻ
(Retail Stores) trên toàn quốc và 1 website thương mại điện tử.

Hiện tại, chúng tôi đang quản lý tồn kho bằng một phần mềm cũ kết hợp với Google
Sheets. Hệ thống này đang gây ra quá nhiều lỗi thất thoát và không thể chịu tải
nổi khi chúng tôi chạy các chiến dịch Flash Sale. Chúng tôi cần đập đi xây lại
một hệ thống Quản lý kho trung tâm (OmniStock) để làm "nguồn sự thật duy nhất"
(Single Source of Truth) cho toàn bộ hàng hóa của công ty.

2. Nỗi đau hiện tại (Pain Points) - Lý do cần xây hệ thống

  - Tình trạng Bán lố (Overselling): Website bán hàng cho khách nhưng thực tế ở
    kho đã hết hàng, nguyên nhân do website và kho cửa hàng không đồng bộ
    real-time.
  - Thất thoát khi luân chuyển: Hàng chuyển từ Kho tổng xuống Cửa hàng mất 3
    ngày, nhưng trong 3 ngày đó trên hệ thống số hàng này "bốc hơi" (không ở
    kho tổng cũng chưa tới cửa hàng), kế toán không biết đường nào để tính giá
    trị tồn kho.
  - Chết server mùa Sale: Vào các ngày Mega Sale (11/11, Black Friday), lượng
    đơn hàng từ website đổ về dồn dập khiến database bị lock, nhân viên tại
    cửa hàng offline không thể thanh toán được cho khách.
  - Không truy vết được lỗi: Khi phát hiện lệch kho, chúng tôi không biết do ai
    làm, do nhập sai, bán sai hay do mất cắp.

3. Quy mô & Thông số dự kiến (Expected Scale & Traffic)

  - Danh mục sản phẩm: ~50,000 SKUs (1 sản phẩm có nhiều màu/size = nhiều SKUs).
  - Số lượng địa điểm kho (Locations): 21 (1 Kho tổng + 20 Cửa hàng).
  - User nội bộ (Admin/Thủ kho/Thu ngân): ~200 users online đồng thời.
  - Lượng đơn xuất/nhập/chuyển nội bộ: ~5,000 giao dịch/ngày.
  - Lượng API call từ Website E-commerce đẩy vào (Check tồn kho & Tạo đơn):
    Trung bình 50-100 requests/s. Đỉnh điểm (Peak time) lên tới 1,000
    requests/s.

4. Yêu cầu Nghiệp vụ cốt lõi (Key Business Use Cases)

Chúng tôi cần hệ thống giải quyết trơn tru các luồng công việc sau:

4.1. Nhập hàng từ Supplier (Inbound):

  - Kho tổng nhận hàng từ Nhà cung cấp. Cần có quy trình: Tạo phiếu Nhập -> Xác
    nhận thực nhận -> Ghi tăng tồn kho.

4.2. Luân chuyển hàng hóa (Stock Transfer):

  - Hỗ trợ chuyển hàng: Kho tổng -> Cửa hàng, hoặc Cửa hàng -> Cửa hàng.
  - Bắt buộc: Hàng đang đi trên đường phải được ghi nhận trạng thái là "In
    Transit" (Đang trung chuyển).
  - Nếu Cửa hàng A gửi 100 cái, nhưng Cửa hàng B nhận được 98 cái, 2 cái thiếu
    phải tự động văng vào một danh sách "Hàng chờ xử lý/Đền bù".

4.3. Bán hàng & Giữ kho (Sales & Stock Reservation):

  - Bán tại cửa hàng (Offline): Khi thu ngân quét mã vạch và in bill, tồn kho
    cửa hàng phải trừ đi NGAY LẬP TỨC.
  - Bán online (E-commerce): Khi khách bấm "Đặt hàng", chưa được trừ tồn kho
    thực tế vội (vì khách có thể hủy đơn hoặc chưa thanh toán), nhưng hệ thống
    phải "Giữ chỗ" (Reserve) số lượng đó để người khác không mua được nữa. Chỉ
    khi đơn giao thành công, mới trừ kho thực. Nếu khách hủy đơn, phải nhả số
    lượng đang giữ ra.

4.4. Kiểm kê định kỳ (Stocktake):

  - Cho phép nhân viên kho dùng máy quét barcode để đếm số lượng thực tế. Cửa
    hàng vẫn được phép bán hàng trong lúc kiểm kê.

5. Yêu cầu Phi chức năng & Kỹ thuật (Non-Functional Requirements)

Chúng tôi không rành về code, nhưng hệ thống phải đảm bảo các tiêu chí sau:

  - Tính chính xác (Accuracy) 100%: Tuyệt đối không bao giờ được phép xảy ra
    tình trạng âm kho (Số lượng < 0) dưới bất kỳ hình thức nào, kể cả khi 10
    người cùng bấm mua 1 sản phẩm cuối cùng cùng 1 giây.
  - Tốc độ (Performance): Website E-commerce khi gọi API hỏi hệ thống OmniStock
    xem "Sản phẩm A còn hàng ở cửa hàng B không?" phải nhận được phản hồi dưới
    200ms.
  - Tính khả dụng (High Availability): Hệ thống kho phải chịu tải độc lập. Nếu
    module báo cáo/thống kê bị sập, các thao tác bán hàng trừ kho vẫn phải hoạt
    động bình thường.
  - Truy vết (Audit Logging): Mọi sự thay đổi về số lượng hàng hóa phải được ghi
    vào Sổ cái (Ledger) không thể tẩy xóa, gồm thông tin: Số lượng thay đổi
    (+/-), Lý do (Bán, Nhập, Chuyển, Mất), Người thực hiện, Thời gian.

6. Lộ trình tương lai (Future Roadmap) - Để thiết kế kiến trúc mở

  - Trong giai đoạn 2 (năm sau), chúng tôi sẽ tích hợp hệ thống này với các đối
    tác giao hàng thứ 3 (GHTK, Viettel Post) qua Webhook.
  - Chúng tôi cũng dự định mở thêm 50 cửa hàng nữa trong 2 năm tới.

🚀 Gợi ý bước tiếp theo dành cho bạn (Người Architect/Developer):

Từ "Đề bài" này, bạn nên bắt đầu quy trình System Design theo các bước sau. Đây
chính xác là cách bạn sẽ tư duy và trình bày trong buổi phỏng vấn:

1.  High-Level Architecture Design:
      - Bạn sẽ chọn Monolithic, Modular Monolith hay Microservices? (Với yêu cầu
        này, Modular Monolith hoặc kiến trúc Event-Driven là cực kỳ phù hợp).
      - Vẽ sơ đồ luồng đi của request (Cửa hàng -> API Gateway -> Service ->
        Database).
2.  Database Choice & Design (ERD):
      - Chọn Database nào? (PostgreSQL là ưu tiên số 1 cho ACID).
      - Thiết kế bảng ra sao để xử lý vụ "In Transit" và "Sổ cái (Ledger)"?
3.  Concurrency Handle (Giải bài toán Race Condition & Overselling):
      - Bạn sẽ dùng Optimistic Locking trong Database hay dùng Redis Distributed
        Lock khi người dùng đặt hàng online?
4.  Performance Optimization:
      - Làm sao để API check tồn kho đáp ứng < 200ms cho 1000 requests/s? (Gợi
        ý: Phải cache tồn kho Available lên Redis).

Bạn muốn chúng ta bắt đầu thảo luận và vẽ/thiết kế từ bước nào trước? (Ví dụ:
"Hãy giúp tôi phân tích và thiết kế System Architecture cho bài toán này" hoặc
"Hãy thiết kế ERD Database giúp tôi").
