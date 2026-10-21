// 取引先マスタ一覧（CustomerMasterList）画面（自動生成 / auto-generated）
// 画面定義から生成されました。手動編集は再生成で失われます。

/** 取引先s（一覧）の 1 行。 */
export interface CustomerMasterListCustomersRow {
  /** 取引先コード */
  mst00004CustomerCD: string;
  /** 取引先請求コード */
  mst00004CustomerInvoiceCD: string;
  /** 取引先区分 */
  mst00004CustomerKbn: string;
  /** 取引先名 */
  mst00004CustomerNM: string;
  /** 取引先略称 */
  mst00004CustomerOmission: string;
  /** 地域コード */
  mst00004AreaCD: string;
  /** 住所1 */
  mst00004Address1: string;
  /** 住所2 */
  mst00004Address2: string;
  /** 住所3 */
  mst00004Address3: string;
}

/** 取引先マスタ一覧画面フォーム。 */
export interface CustomerMasterListForm {
  /** 取引先コード */
  CustomerCD: string;
  /** 取引先名 */
  CustomerNM: string;
  /** 取引先略称 */
  CustomerOmission: string;
  /** ユーザコード */
  UserCD: string;
  /** ユーザ名 */
  UserName: string;
  /** 日付 */
  CreateDate: Date | null;
  /** 取引先区分 */
  CustomerKbn: string;
  /** 取引先s（一覧） */
  Customers: CustomerMasterListCustomersRow[];
}

/** 画面項目の ID（tpl の data-node 属性の値）。 */
export const CustomerMasterListNodes = {
  n_TableMain: 'n:TableMain',
  n_panel1: 'n:panel1',
  v_CustomerCD: 'v:CustomerCD',
  v_CustomerNM: 'v:CustomerNM',
  v_CustomerOmission: 'v:CustomerOmission',
  n_table1: 'n:table1',
  v_UserCD: 'v:UserCD',
  v_UserName: 'v:UserName',
  v_CreateDate: 'v:CreateDate',
  v_CustomerKbn: 'v:CustomerKbn',
  n_buttonGroup2: 'n:buttonGroup2',
  n_btnSearch1: 'n:btnSearch1',
  n_btnClear1: 'n:btnClear1',
  n_btnNew1: 'n:btnNew1',
  n_btnExport1: 'n:btnExport1',
  n_messageView: 'n:messageView',
  v_Customers: 'v:Customers',
  n_SEQ: 'n:SEQ',
  v_Customers_MST00004_CustomerCD: 'v:Customers.MST00004_CustomerCD',
  v_Customers_MST00004_CustomerInvoiceCD: 'v:Customers.MST00004_CustomerInvoiceCD',
  v_Customers_MST00004_CustomerKbn: 'v:Customers.MST00004_CustomerKbn',
  v_Customers_MST00004_CustomerNM: 'v:Customers.MST00004_CustomerNM',
  v_Customers_MST00004_CustomerOmission: 'v:Customers.MST00004_CustomerOmission',
  v_Customers_MST00004_AreaCD: 'v:Customers.MST00004_AreaCD',
  v_Customers_MST00004_Address1: 'v:Customers.MST00004_Address1',
  v_Customers_MST00004_Address2: 'v:Customers.MST00004_Address2',
  v_Customers_MST00004_Address3: 'v:Customers.MST00004_Address3',
  n_btnPanel1: 'n:btnPanel1',
  n_btnDetail: 'n:btnDetail',
  n_btnEdit: 'n:btnEdit',
  n_btnCopy: 'n:btnCopy',
  n_btnDelete: 'n:btnDelete',
} as const;

