// 取引先マスタ詳細（CustomerMasterDetail）画面（自動生成 / auto-generated）
// 画面定義から生成されました。手動編集は再生成で失われます。

/** 取引先マスタ詳細画面フォーム。 */
export interface CustomerMasterDetailForm {
  /** 取引先コード */
  CustomerMST00004_CustomerCD: string;
  /** 取引先請求コード */
  CustomerMST00004_CustomerInvoiceCD: string;
  /** 取引先区分 */
  CustomerMST00004_CustomerKbn: string;
  /** 取引先名 */
  CustomerMST00004_CustomerNM: string;
  /** 取引先略称 */
  CustomerMST00004_CustomerOmission: string;
  /** 地域コード */
  CustomerMST00004_AreaCD: string;
  /** 住所1 */
  CustomerMST00004_Address1: string;
  /** 住所2 */
  CustomerMST00004_Address2: string;
  /** 住所3 */
  CustomerMST00004_Address3: string;
  /** 電話番号 */
  CustomerMST00004_Tel: string;
  /** FAX */
  CustomerMST00004_Fax: string;
  /** 郵便番号 */
  CustomerMST00004_Zip: string;
  /** 発地コード */
  CustomerMST00004_FromPlaceCD: string;
  /** 仕向地コード */
  CustomerMST00004_ToPlaceCD: string;
  /** 支払条件コード */
  CustomerMST00004_PaymentTermsCD: string;
  /** 納入場所 */
  CustomerMST00004_DeliveryLocation: string;
  /** 取引条件コード */
  CustomerMST00004_TradeTermsCD: string;
  /** 通貨 */
  CustomerMST00004_Currency: string;
  /** 部署名 */
  CustomerMST00004_SectionNM: string;
  /** 担当者名 */
  CustomerMST00004_UserCD: string;
  /** メールアドレス */
  CustomerMST00004_MailAddress: string;
  /** 消費税端数処理区分 */
  CustomerMST00004_TaxRoundKBN: string;
  /** 税額通知区分 */
  CustomerMST00004_TaxCalcKBN: string;
  /** 請求書作成フラグ */
  CustomerMST00004_InvoiceCreateFlg: string;
  /** 削除フラグ */
  CustomerMST00004_DelFlg: string;
  /** 作成者 */
  CustomerMST00004_CreateUserCD: string;
  /** 作成日時 */
  CustomerMST00004_CreateDate: Date | null;
  /** 更新者 */
  CustomerMST00004_UpdateUserCD: string;
  /** 更新日時 */
  CustomerMST00004_UpdateDate: Date | null;
}

/** 画面項目の ID（tpl の data-node 属性の値）。 */
export const CustomerMasterDetailNodes = {
  n_TableMain: 'n:TableMain',
  n_Panel: 'n:Panel',
  n_btnReturn: 'n:btnReturn',
  v_Customer: 'v:Customer',
  v_Customer_MST00004_CustomerCD: 'v:Customer.MST00004_CustomerCD',
  v_Customer_MST00004_CustomerInvoiceCD: 'v:Customer.MST00004_CustomerInvoiceCD',
  v_Customer_MST00004_CustomerKbn: 'v:Customer.MST00004_CustomerKbn',
  v_Customer_MST00004_CustomerNM: 'v:Customer.MST00004_CustomerNM',
  v_Customer_MST00004_CustomerOmission: 'v:Customer.MST00004_CustomerOmission',
  v_Customer_MST00004_AreaCD: 'v:Customer.MST00004_AreaCD',
  v_Customer_MST00004_Address1: 'v:Customer.MST00004_Address1',
  v_Customer_MST00004_Address2: 'v:Customer.MST00004_Address2',
  v_Customer_MST00004_Address3: 'v:Customer.MST00004_Address3',
  v_Customer_MST00004_Tel: 'v:Customer.MST00004_Tel',
  v_Customer_MST00004_Fax: 'v:Customer.MST00004_Fax',
  v_Customer_MST00004_Zip: 'v:Customer.MST00004_Zip',
  v_Customer_MST00004_FromPlaceCD: 'v:Customer.MST00004_FromPlaceCD',
  v_Customer_MST00004_ToPlaceCD: 'v:Customer.MST00004_ToPlaceCD',
  v_Customer_MST00004_PaymentTermsCD: 'v:Customer.MST00004_PaymentTermsCD',
  v_Customer_MST00004_DeliveryLocation: 'v:Customer.MST00004_DeliveryLocation',
  v_Customer_MST00004_TradeTermsCD: 'v:Customer.MST00004_TradeTermsCD',
  v_Customer_MST00004_Currency: 'v:Customer.MST00004_Currency',
  v_Customer_MST00004_SectionNM: 'v:Customer.MST00004_SectionNM',
  v_Customer_MST00004_UserCD: 'v:Customer.MST00004_UserCD',
  v_Customer_MST00004_MailAddress: 'v:Customer.MST00004_MailAddress',
  v_Customer_MST00004_TaxRoundKBN: 'v:Customer.MST00004_TaxRoundKBN',
  v_Customer_MST00004_TaxCalcKBN: 'v:Customer.MST00004_TaxCalcKBN',
  v_Customer_MST00004_InvoiceCreateFlg: 'v:Customer.MST00004_InvoiceCreateFlg',
  v_Customer_MST00004_DelFlg: 'v:Customer.MST00004_DelFlg',
  v_Customer_MST00004_CreateUserCD: 'v:Customer.MST00004_CreateUserCD',
  v_Customer_MST00004_CreateDate: 'v:Customer.MST00004_CreateDate',
  v_Customer_MST00004_UpdateUserCD: 'v:Customer.MST00004_UpdateUserCD',
  v_Customer_MST00004_UpdateDate: 'v:Customer.MST00004_UpdateDate',
  n_buttonGroup1: 'n:buttonGroup1',
  n_btnOk: 'n:btnOk',
  n_btnCancel: 'n:btnCancel',
} as const;

