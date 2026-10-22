// ユーザ選択画面（UserPopup）画面（自動生成 / auto-generated）
// 画面定義から生成されました。手動編集は再生成で失われます。

import * as lib from './scriptlib';

/** 担当者マスタ（一覧）の 1 行。 */
export interface UserPopupUsersRow {
  /** 担当者コード */
  mst00002UserCD: string;
  /** 担当者名 */
  mst00002UserNM: string;
  /** ログインID */
  mst00002LoginUsr: string;
  /** パスワード */
  mst00002LoginPwd: string;
  /** メールアドレス */
  mst00002MailAddress: string;
  /** ロール */
  mst00002UserRole: string;
  /** 部署コード */
  mst00002SectionCD: string;
  /** 部署名 */
  mst00005SectionNM: string;
  /** 作成者 */
  mst00002CreateUserCD: string;
  /** 更新者 */
  mst00002UpdateUserCD: string;
}

/** ユーザ選択画面画面フォーム。 */
export interface UserPopupForm {
  /** ユーザコード */
  UserCD: string;
  /** ユーザ名 */
  UserNm: string;
  /** 担当者マスタ（一覧） */
  Users: UserPopupUsersRow[];
}

/** 画面項目の ID（tpl の data-node 属性の値）。 */
export const UserPopupNodes = {
  n_TableMain: 'n:TableMain',
  n_panel1: 'n:panel1',
  v_UserCD: 'v:UserCD',
  v_UserNm: 'v:UserNm',
  v_Users: 'v:Users',
  n_SEQ: 'n:SEQ',
  v_Users_MST00002_UserCD: 'v:Users.MST00002_UserCD',
  v_Users_MST00002_UserNM: 'v:Users.MST00002_UserNM',
  v_Users_MST00002_LoginUsr: 'v:Users.MST00002_LoginUsr',
  v_Users_MST00002_LoginPwd: 'v:Users.MST00002_LoginPwd',
  v_Users_MST00002_MailAddress: 'v:Users.MST00002_MailAddress',
  v_Users_MST00002_UserRole: 'v:Users.MST00002_UserRole',
  v_Users_MST00002_SectionCD: 'v:Users.MST00002_SectionCD',
  v_Users____MST00005_SectionNM: 'v:Users.部署.MST00005_SectionNM',
  n_cellPanel1: 'n:cellPanel1',
  v_Users_MST00002_CreateUserCD: 'v:Users.MST00002_CreateUserCD',
  v_Users_MST00002_UpdateUserCD: 'v:Users.MST00002_UpdateUserCD',
  n_btnPanel1: 'n:btnPanel1',
  n_btnDetail: 'n:btnDetail',
  n_btnEdit: 'n:btnEdit',
  n_btnCopy: 'n:btnCopy',
  n_btnDelete: 'n:btnDelete',
  n_buttonGroup1: 'n:buttonGroup1',
  n_btnOk: 'n:btnOk',
  n_btnCancel: 'n:btnCancel',
} as const;

// ===== ブラウザー側の処理（コードの Front Event と、そこから呼び出す Sub / Function） =====

/** ブラウザー側で使う変数（入力欄・hidden の値はイベントの前に読み込み、後で書き戻す）。 */
const form = {
  UserCD: ('') as string,
  UserNm: ('') as string,
};

/** 画面と同期する変数。 */
const fields: lib.Field[] = [
  { name: 'UserCD', kind: 'string' },
  { name: 'UserNm', kind: 'string' },
];

/**
 * イベントの処理：画面の値を読み込んでから実行し、変数の値を画面に書き戻す。
 * 最初に ClearOnEvent の MessageView を空にする。Throw "メッセージ" は MessageView に Error で表示し、
 * それ以外の捕まえていないエラーは Front Event Form.Error（ない場合は MessageView に Error ＋ コンソール）。
 */
function run(event: string, handler: () => void): void {
  lib.enter(event);
  lib.msgClearOnEvent();
  lib.readForm(fields, form);
  try {
    handler();
  } catch (ex) {
    lib.caught(ex);
    if (ex instanceof lib.ScriptMessage) {
      lib.msg('Error', ex.message);
    } else {
      console.error(ex);
      lib.msg('Error', lib.errDescription(ex) + '（' + lib.errCode(ex) + '）');
    }
  }
  lib.writeForm(fields, form);
}

/** 画面のイベントを登録する。 */
export function bind(): void {
  lib.onRowClick('Users', row => lib.submitEvent('Users_RowClick', row));
  lib.restorePosition();
  lib.onTyping('UserCD', () => lib.submitChange('UserCD_Change'));
  lib.onTyping('UserNm', () => lib.submitChange('UserNm_Change'));
}

bind();
lib.onUpdate(bind);
