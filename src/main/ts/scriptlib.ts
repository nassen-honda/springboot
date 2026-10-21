// コード（画面の「コード」タブ）のブラウザー側の処理が使う組み込み関数と、画面の読み書き（自動生成 / auto-generated）。
// Nothing（null）の数値は 0、文字列は '' として扱う（VB と同じ）。手動編集は再生成で失われます。

export type FieldKind = 'string' | 'number' | 'boolean' | 'date' | 'datetime';

/** 画面と同期する変数（name は入力欄の name 属性）。 */
export interface Field {
  name: string;
  kind: FieldKind;
}

// ===== 型の変換・比較 =====

export function str(value: unknown): string {
  if (value === null || value === undefined) return '';
  if (value instanceof Date) return formatDate(value, 'yyyy/MM/dd');
  return String(value);
}

export function num(value: unknown): number {
  if (value === null || value === undefined || value === '') return 0;
  if (typeof value === 'number') return value;
  if (typeof value === 'boolean') return value ? -1 : 0;
  const n = Number(String(value).replace(/,/g, '').trim());
  return isNaN(n) ? 0 : n;
}

export function bool(value: unknown): boolean {
  if (typeof value === 'boolean') return value;
  if (typeof value === 'number') return value !== 0;
  const s = str(value).trim().toLowerCase();
  return s === 'true' || s === '1' || s === 'on';
}

export function toInt(value: unknown): number {
  return Math.round(num(value));
}

export function toNum(value: unknown): number {
  return num(value);
}

export function toDate(value: unknown): Date | null {
  if (value === null || value === undefined || value === '') return null;
  if (value instanceof Date) return value;
  const d = new Date(String(value).replace(/\//g, '-'));
  return isNaN(d.getTime()) ? null : d;
}

export function isNumeric(value: unknown): boolean {
  if (typeof value === 'number') return true;
  const s = str(value).replace(/,/g, '').trim();
  return s !== '' && !isNaN(Number(s));
}

export function errMessage(ex: unknown): string {
  return ex instanceof Error ? ex.message : str(ex);
}

// ===== エラー（ex）：ブラウザー側は DB を使わないため、種類は常に「その他のエラー」 =====

/** Throw "メッセージ"：メッセージを表示する（Form.Error には渡さない）。 */
export class ScriptMessage extends Error {}

let currentEvent = '';
let currentLine = 0;
const caughtErrors = new WeakMap<object, { time: Date; event: string; line: number }>();

/** イベントの始まり（ex.Event の値）。 */
export function enter(event: string): void {
  currentEvent = event;
  currentLine = 0;
}

/** 今のコードの行（ex.Line の値。文ごとに覚える）。 */
export function at(line: number): void {
  currentLine = line;
}

/** エラーを捕まえた（Catch・Form.Error の前）：日時・イベント・行を覚える。 */
export function caught<T>(ex: T): T {
  if (typeof ex === 'object' && ex !== null && !caughtErrors.has(ex)) {
    caughtErrors.set(ex, { time: new Date(), event: currentEvent, line: currentLine });
  }
  return ex;
}

/** ex.Line：エラーが起きたコードの行（わからない場合は 0）。 */
export function errLine(ex: unknown): number {
  return typeof ex === 'object' && ex !== null ? caughtErrors.get(caught(ex))?.line ?? 0 : 0;
}

export function errCode(_ex: unknown): string {
  return 'その他のエラー';
}

export function errDescription(ex: unknown): string {
  return ex instanceof ScriptMessage ? ex.message : '処理できませんでした。';
}

export function errDetail(ex: unknown): string {
  return ex instanceof Error ? `${ex.name}: ${ex.message}${ex.stack ? '\n' + ex.stack : ''}` : str(ex);
}

export function errEvent(ex: unknown): string {
  return typeof ex === 'object' && ex !== null ? caughtErrors.get(caught(ex))?.event ?? currentEvent : currentEvent;
}

export function errTime(ex: unknown): Date | null {
  return typeof ex === 'object' && ex !== null ? caughtErrors.get(caught(ex))?.time ?? null : null;
}

/** Log(値)：ブラウザーのコンソールに出す（エラーは Error、それ以外は Info）。screen はログの場所（画面 ID）。 */
export function log(value: unknown, screen = ''): void {
  logAt(value instanceof Error ? 'Error' : 'Info', value, screen);
}

/**
 * Log(レベル, 値)：レベル（LogLevel.Error / Warning / Info / Debug）を指定してコンソールに出す（console.error / warn / info / debug）。
 * 開発の実行環境（サーバーが Cookie の script-log を送ったとき）はサーバーにも送る（IDE の出力ウィンドウに出る）。
 */
export function logAt(level: unknown, value: unknown, screen = ''): void {
  const name = str(level).toLowerCase();
  const write = name === 'error' ? console.error : name === 'warning' ? console.warn : name === 'debug' ? console.debug : console.info;
  const where = screen ? `【${screen}${currentLine > 0 ? ` ${currentLine} 行目` : ''}】` : '';
  const text = value instanceof Error ? `${errDescription(value)}（${errCode(value)}）イベント：${errEvent(value)}　${errDetail(value)}` : str(value);
  if (value instanceof Error) {
    write(`${where}${errDescription(value)}（${errCode(value)}）イベント：${errEvent(value)}`, value);
  } else {
    write(where + text);
  }
  sendLog(str(level) || 'Info', screen, currentLine, text);
}

// サーバーに送らない（Cookie がない・送れなかった）
let logOff = false;

function sendLog(level: string, screen: string, line: number, text: string): void {
  if (logOff || !/(^|;\s*)script-log=1(;|$)/.test(document.cookie)) return;
  try {
    fetch('/script/log', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ level, screen, line, text }),
      keepalive: true,
    }).then(r => { if (!r.ok) logOff = true; }, () => { logOff = true; });
  } catch {
    logOff = true;
  }
}

/** Log(&a, &b, "文字列", …)：名前と値の組を「&a = 1, &b = "x", 文字列」の形にする（名前が null の値は文字列としてそのまま）。 */
export function vars(...pairs: unknown[]): string {
  const parts: string[] = [];
  for (let i = 0; i + 1 < pairs.length; i += 2) {
    parts.push(pairs[i] === null ? str(pairs[i + 1]) : `${pairs[i]} = ${display(pairs[i + 1])}`);
  }
  return parts.join(', ');
}

/** 値を確認用の文字にする（文字列は "" で囲み、空は Nothing、配列・Map・オブジェクトは中身を並べる）。 */
export function display(value: unknown, depth = 0): string {
  if (value === null || value === undefined) return 'Nothing';
  if (typeof value === 'string') return `"${value.length > 500 ? value.slice(0, 500) + '…' : value}"`;
  if (value instanceof Error) return `${value.name}: ${errDescription(value)}`;
  if (value instanceof Date || typeof value !== 'object') return str(value);
  if (depth >= 4) return '…';
  if (Array.isArray(value)) {
    const items = value.slice(0, 20).map(v => display(v, depth + 1));
    if (value.length > 20) items.push(`…（全 ${value.length} 件）`);
    return `[${items.join(', ')}]`;
  }
  if (value instanceof Map) {
    const items = [...value.entries()].slice(0, 20).map(([k, v]) => `${display(k, depth + 1)}: ${display(v, depth + 1)}`);
    if (value.size > 20) items.push(`…（全 ${value.size} 件）`);
    return `{${items.join(', ')}}`;
  }
  return `{${Object.entries(value as object).map(([k, v]) => `${k}: ${display(v, depth + 1)}`).join(', ')}}`;
}

/** 値が空：null・''・0・要素が 0 件の配列 / Map。 */
export function isEmpty(value: unknown): boolean {
  if (value === null || value === undefined || value === '' || value === 0) return true;
  if (Array.isArray(value)) return value.length === 0;
  if (value instanceof Map) return value.size === 0;
  return false;
}

export function cmp(a: unknown, b: unknown): number {
  if ((a === null || a === undefined) && (b === null || b === undefined)) return 0;
  if (typeof a === 'number' || typeof b === 'number') return Math.sign(num(a) - num(b));
  if (a instanceof Date || b instanceof Date) return Math.sign((toDate(a)?.getTime() ?? 0) - (toDate(b)?.getTime() ?? 0));
  if (typeof a === 'boolean' || typeof b === 'boolean') return Math.sign(Number(bool(a)) - Number(bool(b)));
  const x = str(a);
  const y = str(b);
  return x < y ? -1 : x > y ? 1 : 0;
}

export function eq(a: unknown, b: unknown): boolean {
  return cmp(a, b) === 0;
}

export function forCondition(i: unknown, end: unknown, step: unknown): boolean {
  return num(step) < 0 ? cmp(i, end) >= 0 : cmp(i, end) <= 0;
}

/** 整数の割り算（小数点以下を切り捨て。10 進数で正確に：0.3 \ 0.1 = 3）。 */
export function idiv(a: unknown, b: unknown): number {
  const x = toDec(a);
  const y = toDec(b);
  if (y.v === ZERO) throw new Error('0 で割ることはできません。');
  return Number((x.v * pow10(y.s)) / (y.v * pow10(x.s)));
}

// ===== 小数の計算（10 進数で正確に計算する：0.1 + 0.2 = 0.3、1.1 * 3 = 3.3） =====
// 値は number で持ち、計算は「整数（BigInt）÷ 10 の小数部の桁数乗」で行う（サーバーの BigDecimal と同じ結果）。
// number で持つため、正確なのは有効桁数 15 桁まで。

/** 10 進数の値：v ÷ 10^s。 */
interface Dec {
  v: bigint;
  s: number;
}

const big = (window as unknown as { BigInt: (value: string | number) => bigint }).BigInt;
const ZERO = big(0);
const ONE = big(1);
const TWO = big(2);

function pow10(n: number): bigint {
  return big('1' + '0'.repeat(Math.max(0, n)));
}

/** 数値を 10 進数にする（number の最短の 10 進数の表記から。0.1 は 0.1 のまま）。 */
function toDec(value: unknown): Dec {
  const n = num(value);
  if (!isFinite(n)) return { v: ZERO, s: 0 };
  let text = String(n);
  let exponent = 0;
  const e = text.search(/e/i);
  if (e >= 0) {
    exponent = Number(text.slice(e + 1));
    text = text.slice(0, e);
  }
  const negative = text.startsWith('-');
  if (negative) text = text.slice(1);
  const dot = text.indexOf('.');
  let digits = dot < 0 ? text : text.slice(0, dot) + text.slice(dot + 1);
  let s = (dot < 0 ? 0 : text.length - dot - 1) - exponent;
  if (s < 0) {
    digits += '0'.repeat(-s);
    s = 0;
  }
  const v = big(digits);
  return { v: negative ? -v : v, s };
}

/** 10 進数の表記（v ÷ 10^s。指数表記にしない）。 */
function decText(v: bigint, s: number): string {
  const negative = v < ZERO;
  let digits = (negative ? -v : v).toString();
  if (s > 0) {
    digits = digits.padStart(s + 1, '0');
    digits = digits.slice(0, digits.length - s) + '.' + digits.slice(digits.length - s);
  }
  return (negative ? '-' : '') + digits;
}

function fromDec(v: bigint, s: number): number {
  return Number(decText(v, s));
}

/** v ÷ 10^s を小数部 digits 桁に丸めた整数（÷ 10^digits が値）。halfEven は偶数丸め（Format）、それ以外は四捨五入（Round）。 */
function roundScaled(v: bigint, s: number, digits: number, halfEven: boolean): bigint {
  if (digits >= s) return v * pow10(digits - s);
  return divRound(v, pow10(s - digits), halfEven);
}

/** a ÷ b を整数に丸める（halfEven は偶数丸め、それ以外は四捨五入。0 から遠い方へ）。 */
function divRound(a: bigint, b: bigint, halfEven: boolean): bigint {
  const negative = (a < ZERO) !== (b < ZERO);
  const x = a < ZERO ? -a : a;
  const y = b < ZERO ? -b : b;
  let q = x / y;
  const twice = (x % y) * TWO;
  if (twice > y || (twice === y && (!halfEven || q % TWO === ONE))) q += ONE;
  return negative ? -q : q;
}

function aligned(a: unknown, b: unknown): [bigint, bigint, number] {
  const x = toDec(a);
  const y = toDec(b);
  const s = Math.max(x.s, y.s);
  return [x.v * pow10(s - x.s), y.v * pow10(s - y.s), s];
}

export function decAdd(a: unknown, b: unknown): number {
  const [x, y, s] = aligned(a, b);
  return fromDec(x + y, s);
}

export function decSub(a: unknown, b: unknown): number {
  const [x, y, s] = aligned(a, b);
  return fromDec(x - y, s);
}

export function decMul(a: unknown, b: unknown): number {
  const x = toDec(a);
  const y = toDec(b);
  return fromDec(x.v * y.v, x.s + y.s);
}

// 割り算の結果の小数部の桁数（number の精度より十分に多い）
const DIV_SCALE = 40;

export function decDiv(a: unknown, b: unknown): number {
  const x = toDec(a);
  const y = toDec(b);
  if (y.v === ZERO) throw new Error('0 で割ることはできません。');
  // (x.v ÷ 10^x.s) ÷ (y.v ÷ 10^y.s) を小数部 DIV_SCALE 桁に丸める
  return fromDec(divRound(x.v * pow10(y.s + DIV_SCALE), y.v * pow10(x.s), true), DIV_SCALE);
}

export function decMod(a: unknown, b: unknown): number {
  const [x, y, s] = aligned(a, b);
  if (y === ZERO) throw new Error('0 で割ることはできません。');
  return fromDec(x % y, s);
}

/** Round(数値, 桁数)：四捨五入（10 進数で正確に。Round(1.005, 2) = 1.01）。 */
export function round(value: unknown, digits: number): number {
  const d = toDec(value);
  if (digits >= 0) return fromDec(roundScaled(d.v, d.s, digits, false), digits);
  return fromDec(roundScaled(d.v, d.s, digits, false) * pow10(-digits), 0);
}

/** 小数点以下 digits 桁にする：down は 0 の方へ（Truncate）、floor は小さい方へ（Floor）、ceiling は大きい方へ（Ceiling）。 */
function directed(value: unknown, digits: number, mode: 'down' | 'floor' | 'ceiling'): number {
  const d = toDec(value);
  if (digits >= d.s) return fromDec(d.v, d.s);
  const b = pow10(d.s - digits);
  let q = d.v / b;   // 0 の方へ
  const r = d.v % b;
  if (r !== ZERO && mode === 'floor' && r < ZERO) q -= ONE;
  if (r !== ZERO && mode === 'ceiling' && r > ZERO) q += ONE;
  return digits >= 0 ? fromDec(q, digits) : fromDec(q * pow10(-digits), 0);
}

/** Truncate(数値, 桁数)：小数点以下 桁数 桁で切り捨て（0 の方へ）。 */
export function truncate(value: unknown, digits: number): number {
  return directed(value, digits, 'down');
}

/** Floor(数値, 桁数)：小数点以下 桁数 桁の、小さい方の値。 */
export function floor(value: unknown, digits: number): number {
  return directed(value, digits, 'floor');
}

/** Ceiling(数値, 桁数)：小数点以下 桁数 桁の、大きい方の値（切り上げ）。 */
export function ceiling(value: unknown, digits: number): number {
  return directed(value, digits, 'ceiling');
}

// ===== 文字列 =====

export function len(s: unknown): number {
  return str(s).length;
}

export function left(s: unknown, n: number): string {
  return str(s).substring(0, Math.max(0, n));
}

export function right(s: unknown, n: number): string {
  const t = str(s);
  return t.substring(Math.max(0, t.length - Math.max(0, n)));
}

/** LPad(文字列, 文字数, 文字)：左に文字を埋めて 文字数 にする（長い場合は先頭から 文字数。文字が空の場合は空白）。 */
export function lpad(s: unknown, n: number, c: unknown): string {
  return pad(s, n, c, true);
}

/** RGB(赤, 緑, 青)：色の番号（#RRGGBB）。0 ～ 255 の外は 0・255 にする。 */
export function rgb(r: number, g: number, b: number): string {
  return "#" + [r, g, b].map(v => Math.max(0, Math.min(255, Math.trunc(Number(v) || 0))).toString(16).toUpperCase().padStart(2, "0")).join("");
}

/** RPad(文字列, 文字数, 文字)：右に文字を埋めて 文字数 にする。 */
export function rpad(s: unknown, n: number, c: unknown): string {
  return pad(s, n, c, false);
}

function pad(s: unknown, n: number, c: unknown, left: boolean): string {
  const t = str(s);
  const length = Math.max(0, n);
  if (t.length >= length) return t.substring(0, length);
  const padding = (str(c) === '' ? ' ' : str(c).charAt(0)).repeat(length - t.length);
  return left ? padding + t : t + padding;
}

export function mid(s: unknown, start: number, length?: number): string {
  const t = str(s).substring(Math.max(0, start - 1));
  return length === undefined ? t : t.substring(0, Math.max(0, length));
}

export function trim(s: unknown): string { return str(s).trim(); }
export function ltrim(s: unknown): string { return str(s).replace(/^\s+/, ''); }
export function rtrim(s: unknown): string { return str(s).replace(/\s+$/, ''); }
export function ucase(s: unknown): string { return str(s).toUpperCase(); }
export function lcase(s: unknown): string { return str(s).toLowerCase(); }

export function instr(a: unknown, b: unknown, c?: unknown): number {
  return c === undefined ? str(a).indexOf(str(b)) + 1 : str(b).indexOf(str(c), Math.max(0, num(a) - 1)) + 1;
}

export function replace(s: unknown, find: unknown, replacement: unknown): string {
  const f = str(find);
  return f === '' ? str(s) : str(s).split(f).join(str(replacement));
}

export function listRemove<T>(list: T[], value: T): void {
  const i = list.findIndex(x => eq(x, value));
  if (i >= 0) list.splice(i, 1);
}

/** AddAll(&一覧2)：一覧 2 の要素をすべて最後に追加する（自分自身も可）。 */
export function addAll<T>(list: T[], values: T[] | null): void {
  if (values) list.push(...values.slice());
}

/** Clone()：構造体・List・Map のコピー（中の構造体・List・Map・日付もコピーする）。コピーを変更しても元は変わらない。 */
export function copyOf<T>(value: T): T {
  return value === null || value === undefined ? value : structuredClone(value);
}

/** Sort([Desc])：並べ替える（Null は昇順で先頭）。 */
export function sort(list: unknown[], desc: boolean): void {
  list.sort((a, b) => {
    const r = a === null || a === undefined ? (b === null || b === undefined ? 0 : -1) : b === null || b === undefined ? 1 : cmp(a, b);
    return desc ? -r : r;
  });
}

// ===== 日付 =====

export function today(): Date {
  const d = new Date();
  return new Date(d.getFullYear(), d.getMonth(), d.getDate());
}

export function dateAdd(interval: string, amount: number, date: Date | null): Date | null {
  if (!date) return null;
  const d = new Date(date.getTime());
  switch (interval.toLowerCase()) {
    case 'yyyy': case 'y': d.setFullYear(d.getFullYear() + amount); break;
    case 'm': d.setMonth(d.getMonth() + amount); break;
    case 'ww': case 'w': d.setDate(d.getDate() + amount * 7); break;
    case 'h': d.setHours(d.getHours() + amount); break;
    case 'n': d.setMinutes(d.getMinutes() + amount); break;
    case 's': d.setSeconds(d.getSeconds() + amount); break;
    default: d.setDate(d.getDate() + amount); break;
  }
  return d;
}

export function dateDiff(interval: string, from: Date | null, to: Date | null): number {
  if (!from || !to) return 0;
  const ms = to.getTime() - from.getTime();
  switch (interval.toLowerCase()) {
    case 'yyyy': case 'y': return to.getFullYear() - from.getFullYear();
    case 'm': return (to.getFullYear() - from.getFullYear()) * 12 + to.getMonth() - from.getMonth();
    case 'ww': case 'w': return Math.trunc(ms / 604800000);
    case 'h': return Math.trunc(ms / 3600000);
    case 'n': return Math.trunc(ms / 60000);
    case 's': return Math.trunc(ms / 1000);
    default: return Math.trunc(ms / 86400000);
  }
}

export function year(d: Date | null): number { return d ? d.getFullYear() : 0; }
export function month(d: Date | null): number { return d ? d.getMonth() + 1 : 0; }
export function day(d: Date | null): number { return d ? d.getDate() : 0; }

/** LastDay(日付)：その月の末日（時刻は 0 時）。 */
export function lastDay(value: unknown): Date | null {
  const d = toDate(value);
  return d ? new Date(d.getFullYear(), d.getMonth() + 1, 0) : null;
}

function formatDate(d: Date, pattern: string): string {
  const pad = (n: number, w: number) => String(n).padStart(w, '0');
  return pattern
    .replace(/yyyy/g, String(d.getFullYear()))
    .replace(/MM/g, pad(d.getMonth() + 1, 2))
    .replace(/dd/g, pad(d.getDate(), 2))
    .replace(/HH/g, pad(d.getHours(), 2))
    .replace(/mm/g, pad(d.getMinutes(), 2))
    .replace(/ss/g, pad(d.getSeconds(), 2));
}

/** Format(値, 書式)：数値は #,##0.00 など、日付は yyyy/MM/dd など。 */
export function format(value: unknown, pattern: string): string {
  if (value === null || value === undefined) return '';
  if (typeof value === 'string') return formatText(value, pattern);
  if (value instanceof Date) return formatDate(value, pattern);
  if (typeof value === 'number') {
    // 10 進数で丸める（サーバーの DecimalFormat と同じ偶数丸め）
    const fraction = pattern.split('.')[1] ?? '';
    const maxDigits = fraction.replace(/[^0#]/g, '').length;
    const minDigits = fraction.replace(/[^0]/g, '').length;
    const d = toDec(value);
    let text = decText(roundScaled(d.v, d.s, maxDigits, true), maxDigits);
    const negative = text.startsWith('-');
    if (negative) text = text.slice(1);
    let [integer, decimals = ''] = text.split('.');
    while (decimals.length > minDigits && decimals.endsWith('0')) decimals = decimals.slice(0, -1);
    if (pattern.includes(',')) integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    const result = integer + (decimals.length > 0 ? '.' + decimals : '');
    return negative && /[1-9]/.test(result) ? '-' + result : result;
  }
  return str(value);
}

/** Format(文字列, 値1, 値2, …)：%1・%2 … を値の文字列にする（%% は %。値がない %n はそのまま）。 */
export function formatText(template: unknown, ...values: unknown[]): string {
  return str(template).replace(/%(%|\d+)/g, (text: string, g: string) => {
    if (g === '%') return '%';
    const n = g.length < 10 ? Number(g) : 0;
    return n >= 1 && n <= values.length ? str(values[n - 1]) : text;
  });
}

// ===== 画面 =====

function element(name: string): HTMLElement | null {
  return document.querySelector<HTMLElement>(`[name="${name}"], [data-item="${name}"]`);
}

export function msgBox(message: string): void {
  window.alert(message);
}

/**
 * Back：ブラウザーの「戻る」と同じ（前のページ）。履歴がない場合（新しいタブで開いた等）はトップ（画面の 1 つ上の階層）へ。
 * ダイアログ（ポップアップの画面）の中では、ダイアログを閉じて開いた画面を表示し直す（値を返す場合は Close を使う）。
 */
export function back(): void {
  if (window.top && window.top !== window) {
    window.top.location.replace(window.top.location.pathname);
    return;
  }
  if (window.history.length > 1) {
    window.history.back();
  } else {
    window.location.assign(new URL('./', window.location.href).href);
  }
}

// ===== MessageView（Msg・MsgClear・MsgCount。画面の補助スクリプトの window.screenMessages が表示する） =====

/** メッセージ 1 件（コードの Message 型）。level は Error / Warning / Info、item は項目の名前（クリックでその入力欄へ）。 */
export interface MessageItem {
  level: string;
  description: string;
  item: string;
}

interface ScreenMessages {
  add(level: string, description: string, item: string): boolean;
  clear(onEventOnly: boolean): void;
  count(level: string | null): number;
}

function screenMessages(): ScreenMessages | undefined {
  return (window as unknown as { screenMessages?: ScreenMessages }).screenMessages;
}

function normalizeLevel(level: unknown): string {
  const s = str(level).toLowerCase();
  return s === 'error' ? 'Error' : s === 'warning' ? 'Warning' : 'Info';
}

/** NewMessage(レベル, 文字列[, 項目])。 */
export function newMessage(level: unknown, description: unknown, item?: unknown): MessageItem {
  return { level: normalizeLevel(level), description: str(description), item: item == null ? '' : str(item) };
}

/** Msg(&m) / Msg(レベル, 文字列[, 項目])：MessageView に書く（MessageView がない画面は alert）。 */
export function msg(message: MessageItem | string, description?: unknown, item?: unknown): void {
  const m = typeof message === 'string' ? newMessage(message, description, item) : message;
  if (!m || !m.description) return;
  if (!screenMessages()?.add(normalizeLevel(m.level), m.description, m.item ?? '')) window.alert(m.description);
}

/** Msg(&一覧)：List(Message) をすべて書く。 */
export function msgAll(messages: MessageItem[]): void {
  (messages ?? []).forEach(m => msg(m));
}

/** MsgClear：MessageView を空にする。 */
export function msgClear(): void {
  screenMessages()?.clear(false);
}

/** Front Event の最初：ClearOnEvent の MessageView を空にする。 */
export function msgClearOnEvent(): void {
  screenMessages()?.clear(true);
}

/** MsgCount([レベル])：MessageView のメッセージの件数（レベルが null の場合はすべて）。 */
export function msgCount(level: string | null): number {
  return screenMessages()?.count(level == null ? null : normalizeLevel(level)) ?? 0;
}

export function confirm(message: string): boolean {
  return window.confirm(message);
}

export function setFocus(name: string): void {
  element(name)?.focus();
}

export function enable(name: string, enabled: boolean): void {
  const e = element(name) as HTMLInputElement | HTMLButtonElement | null;
  if (e) e.disabled = !enabled;
  // 和暦の日付・日時：和暦の入力欄・📅 も同じにする
  if (e?.classList.contains('wareki-native')) e.dispatchEvent(new Event('wareki-sync'));
}

// 入力欄の値（種類に合わせた値）
function readInput(e: HTMLInputElement, kind: FieldKind): unknown {
  switch (kind) {
    case 'number': return num(e.value);
    case 'boolean': return e.type === 'checkbox' ? e.checked : bool(e.value);
    case 'date': case 'datetime': return toDate(e.value);
    default: return e.value;
  }
}

// 入力欄に値を書く
function writeInput(e: HTMLInputElement, kind: FieldKind, value: unknown): void {
  if (kind === 'boolean' && e.type === 'checkbox') {
    e.checked = bool(value);
  } else if ((kind === 'date' || kind === 'datetime') && value instanceof Date) {
    e.value = formatDate(value, kind === 'date' ? 'yyyy-MM-dd' : 'yyyy-MM-ddTHH:mm');
  } else {
    e.value = str(value);
  }
  // 和暦の日付・日時：送信する入力欄に書いた値を和暦の入力欄に表示する
  if (e.classList.contains('wareki-native')) e.dispatchEvent(new Event('wareki-sync'));
}

/** 画面の入力欄の値を変数に読み込む。 */
export function readForm(fields: Field[], form: Record<string, unknown>): void {
  for (const f of fields) {
    const e = element(f.name) as HTMLInputElement | null;
    if (e) form[f.name] = readInput(e, f.kind);
  }
}

/** 変数の値を画面の入力欄に書き戻す。 */
export function writeForm(fields: Field[], form: Record<string, unknown>): void {
  for (const f of fields) {
    const e = element(f.name) as HTMLInputElement | null;
    if (e) writeInput(e, f.kind, form[f.name]);
  }
}

/** 入力欄・ボタンのイベントを登録する。 */
export function on(name: string, event: string, handler: () => void): void {
  element(name)?.addEventListener(event, handler);
}

// 画面の部分更新（画面の補助スクリプト。送信を fetch で送り、ページを読み込み直さない）
interface ScreenUpdate {
  onUpdate(f: (form: HTMLFormElement) => void): void;
  submit(extra: Record<string, string>): void;
}
const screenUpdate = (): ScreenUpdate | undefined => (window as unknown as { screenUpdate?: ScreenUpdate }).screenUpdate;

/** 画面の部分更新で画面の中身を入れ替えた後に実行する（画面のイベントを登録し直す）。 */
export function onUpdate(f: () => void): void {
  screenUpdate()?.onUpdate(() => f());
}

/** サーバーのイベントを実行する（画面を送信する）。 */
export function submitEvent(event: string, row?: number): void {
  const form = document.querySelector('form');
  if (!form) return;
  // 画面の部分更新：ページを読み込み直さない
  const update = screenUpdate();
  if (update) {
    submitting = true;
    update.submit(row === undefined ? { _event: event } : { _event: event, _row: String(row) });
    return;
  }
  const add = (name: string, value: string) => {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
  };
  add('_event', event);
  if (row !== undefined) add('_row', String(row));
  submitting = true;
  form.submit();
}

// 画面を送信した（submitEvent・ボタンの送信）。送信した後はイベントを送らない
let submitting = false;
document.querySelector('form')?.addEventListener('submit', () => { submitting = true; });
// 画面の部分更新の後（ページを読み込み直さない）：また送信できる
// （ページを読み込み直さないため、送信の前に保存したフォーカス・スクロールの位置は使わない）
document.addEventListener('screen-updated', () => {
  submitting = false;
  try { sessionStorage.removeItem(positionKey); } catch { /* 保存していない */ }
});

// マウスのボタンを押している項目（ボタンにフォーカスが移らないブラウザーでも、押しているボタンが分かるように）
let pressed: Element | null = null;
document.addEventListener('pointerdown', e => { pressed = e.target as Element | null; }, true);
document.addEventListener('pointerup', () => setTimeout(() => { pressed = null; }, 0), true);

const positionKey = 'screen-position:' + location.pathname;

// 送信した後の画面で戻すフォーカス（次の入力欄）・スクロールの位置
function savePosition(): void {
  try {
    const active = document.activeElement as HTMLElement | null;
    sessionStorage.setItem(positionKey, JSON.stringify({
      id: active?.id ?? '', name: active?.getAttribute('name') ?? '', x: window.scrollX, y: window.scrollY, time: Date.now(),
    }));
  } catch {
    // 保存できない場合は戻さない
  }
}

/** 入力欄の Change / LostFocus で送信した後：フォーカス（次の入力欄）・スクロールの位置を戻す（30 秒以内に送信した場合だけ）。 */
export function restorePosition(): void {
  try {
    const raw = sessionStorage.getItem(positionKey);
    if (!raw) return;
    sessionStorage.removeItem(positionKey);
    const p = JSON.parse(raw) as { id: string; name: string; x: number; y: number; time: number };
    if (Date.now() - p.time > 30000) return;
    window.scrollTo(p.x, p.y);
    const target = (p.id && document.getElementById(p.id)) || (p.name && document.getElementsByName(p.name)[0]) || null;
    if (target instanceof HTMLElement) target.focus({ preventScroll: true });
  } catch {
    // 戻せない場合はそのまま
  }
}

/**
 * 入力欄・一覧のセルの Change / LostFocus（サーバーのイベント）：画面を送信する。
 * ボタンを押している途中（フォーカスがボタンへ移った）の場合は、ボタンの送信と一緒に送る（サーバーはこのイベントを先に実行する。_change）。
 * ボタンが送信しない（Front Event のボタン・押さずに離れた）場合は、このイベントだけ送信する。
 */
export function submitChange(event: string, row?: number): void {
  const form = document.querySelector('form');
  // 画面の部分更新では、送信中でも送る（前の送信の後に順に送る。入力中の値を落とさない）
  const blocked = () => submitting && !screenUpdate();
  if (!form || blocked()) return;
  // フォーカスが次の項目へ移ってから決める
  setTimeout(() => {
    if (blocked()) return;
    const active = document.activeElement;
    const button = (pressed ?? active)?.closest<HTMLElement>('button, input[type="submit"], input[type="button"], a');
    if (button && form.contains(button)) {
      const add = (name: string, value: string) => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = value;
        // 送信の後に消す（画面の部分更新でダウンロードした場合など、画面を入れ替えないとき）
        input.dataset.transient = '1';
        form.appendChild(input);
      };
      add('_change', event);
      if (row !== undefined) add('_changeRow', String(row));
      savePosition();
      let done = false;
      const finish = () => {
        if (done) return;
        done = true;
        setTimeout(() => { if (!submitting) submitEvent(event, row); }, 0);
      };
      button.addEventListener('click', finish, { once: true });
      button.addEventListener('blur', finish, { once: true });
      return;
    }

    savePosition();
    submitEvent(event, row);
  }, 0);
}

/** 一覧（DataGrid）の行のクリック（行番号は 1 から）。セルのボタン・リンク・入力欄のクリックは行のクリックにしない。 */
export function onRowClick(grid: string, handler: (row: number) => void): void {
  document.querySelectorAll<HTMLElement>(`[data-grid="${grid}"] [data-row]`).forEach(tr => {
    tr.addEventListener('click', e => {
      if ((e.target as Element | null)?.closest('button, a, input, select, textarea, label')) return;
      handler(num(tr.dataset.row));
    });
  });
}

// ===== 一覧（DataGrid）の行のセル（&一覧(row).項目） =====

// 一覧の行（row は 1 から）の列のセル（td の data-col）
function cellOf(grid: string, row: number, column: string): HTMLElement | null {
  return document.querySelector<HTMLElement>(`[data-grid="${grid}"] tr[data-row="${row}"] [data-col="${column}"]`);
}

function cellInput(cell: HTMLElement): HTMLInputElement | null {
  return cell.querySelector<HTMLInputElement>('input[name], select[name], textarea[name]');
}

/** 一覧の行のセルの値：入力欄はその値、表示だけのセルは data-value（書式を付ける前の値）。 */
export function cellGet(grid: string, row: number, column: string, kind: FieldKind): unknown {
  const cell = cellOf(grid, row, column);
  if (!cell) return kind === 'number' ? 0 : kind === 'boolean' ? false : kind === 'string' ? '' : null;
  const input = cellInput(cell);
  if (input) return readInput(input, kind);
  const raw = cell.dataset.value ?? '';
  switch (kind) {
    case 'number': return num(raw);
    case 'boolean': return raw === 'true';
    case 'date': case 'datetime': return toDate(raw);
    default: return raw;
  }
}

/**
 * 一覧の行のセルに値を書く：入力欄はその値（一覧と一緒に送信する）、表示だけのセルは表示（書式を付ける）。
 * 表示だけのセルは送信しない（サーバーの値は変わらない）。
 */
export function cellSet(grid: string, row: number, column: string, kind: FieldKind, value: unknown, pattern: string): void {
  const cell = cellOf(grid, row, column);
  if (!cell) return;
  const input = cellInput(cell);
  if (input) {
    writeInput(input, kind, value);
    return;
  }
  cell.dataset.value = value instanceof Date
    ? formatDate(value, kind === 'date' ? 'yyyy-MM-dd' : 'yyyy-MM-ddTHH:mm:ss')
    : value === null || value === undefined ? '' : String(value);
  const text = pattern ? format(value, pattern) : str(value);
  // リンクの列は中のボタン・文字
  const target = cell.querySelector<HTMLElement>('button, span:not(.error)') ?? cell;
  target.textContent = text;
}

/**
 * 一覧のセルのイベント（selector：ボタン・リンクは [data-item="名前"]、入力欄は [data-col="列"] input など）。
 * 行番号（1 から）を渡して実行する。
 */
export function onCell(grid: string, selector: string, event: string, handler: (row: number) => void): void {
  document.querySelectorAll<HTMLElement>(`[data-grid="${grid}"]`).forEach(table => {
    table.addEventListener(event, e => {
      const target = (e.target as Element | null)?.closest(selector);
      const tr = target?.closest<HTMLElement>('tr[data-row]');
      if (!target || !tr || !table.contains(tr)) return;
      handler(num(tr.dataset.row));
    });
  });
}

// Change（入力中）：入力が止まってから送信するまでの時間（ミリ秒）
const typingDelay = 400;

// 入力欄の今の値（チェック ボックス・ラジオは選択の状態）
function valueOf(e: Element): string {
  if (e instanceof HTMLInputElement && (e.type === 'checkbox' || e.type === 'radio')) return String(e.checked);
  return (e as HTMLInputElement).value ?? '';
}

// 入力欄 e の Change（入力中）：入力（input）が typingDelay 止まったとき、または値が決まったとき（change：フォーカスが外れた・Enter・選択）に、
// 前に送った値と違えば fire を実行する。日本語の変換中（IME）は送らない（変換を確定してから）
const typingStates = new WeakMap<Element, { timer: number; last: string }>();
function typing(e: Element, event: Event, fire: () => void): void {
  let state = typingStates.get(e);
  if (!state) {
    // 前に送った値：最初は画面を表示したときの値（サーバーが書いた値）
    state = { timer: 0, last: (e as HTMLInputElement).defaultValue ?? '' };
    if (e instanceof HTMLInputElement && (e.type === 'checkbox' || e.type === 'radio')) state.last = String(e.defaultChecked);
    if (e instanceof HTMLSelectElement) state.last = [...e.options].find(o => o.defaultSelected)?.value ?? e.value;
    typingStates.set(e, state);
  }
  const s = state;
  const run = () => {
    clearTimeout(s.timer);
    s.timer = 0;
    const value = valueOf(e);
    if (value === s.last) return;
    s.last = value;
    fire();
  };
  if (event.type === 'change') {
    run();
  } else if (!(event as InputEvent).isComposing) {
    clearTimeout(s.timer);
    s.timer = window.setTimeout(run, typingDelay);
  }
}

/** 入力欄の Change（サーバー）：入力中は入力が少し止まったとき、選択・チェック・フォーカスが外れたときはすぐ。 */
export function onTyping(name: string, handler: () => void): void {
  const e = element(name);
  if (!e) return;
  for (const type of ['input', 'compositionend', 'change']) e.addEventListener(type, ev => typing(e, ev, handler));
}

/** 一覧のセルの入力欄の Change（サーバー）：onTyping と同じ。行番号（1 から）を渡す。 */
export function onCellTyping(grid: string, selector: string, handler: (row: number) => void): void {
  document.querySelectorAll<HTMLElement>(`[data-grid="${grid}"]`).forEach(table => {
    for (const type of ['input', 'compositionend', 'change']) {
      table.addEventListener(type, ev => {
        const target = (ev.target as Element | null)?.closest(selector);
        const tr = target?.closest<HTMLElement>('tr[data-row]');
        if (!target || !tr || !table.contains(tr)) return;
        typing(target, ev, () => handler(num(tr.dataset.row)));
      });
    }
  });
}
