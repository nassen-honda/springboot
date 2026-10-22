// 自動生成 / auto-generated：src/main/ts の画面の TypeScript を esbuild でまとめたもの。変更は再生成で失われます。

// scriptlib.ts
function num(value) {
  if (value === null || value === void 0 || value === "") return 0;
  if (typeof value === "number") return value;
  if (typeof value === "boolean") return value ? -1 : 0;
  const n = Number(String(value).replace(/,/g, "").trim());
  return isNaN(n) ? 0 : n;
}
var big = window.BigInt;
var ZERO = big(0);
var ONE = big(1);
var TWO = big(2);
function element(name) {
  return document.querySelector(`[name="${name}"], [data-item="${name}"]`);
}
var screenUpdate = () => window.screenUpdate;
function onUpdate(f) {
  screenUpdate()?.onUpdate(() => f());
}
function submitEvent(event, row) {
  const form = document.querySelector("form");
  if (!form) return;
  const update = screenUpdate();
  if (update) {
    submitting = true;
    update.submit(row === void 0 ? { _event: event } : { _event: event, _row: String(row) });
    return;
  }
  const add = (name, value) => {
    const input = document.createElement("input");
    input.type = "hidden";
    input.name = name;
    input.value = value;
    form.appendChild(input);
  };
  add("_event", event);
  if (row !== void 0) add("_row", String(row));
  submitting = true;
  form.submit();
}
var submitting = false;
document.querySelector("form")?.addEventListener("submit", () => {
  submitting = true;
});
document.addEventListener("screen-updated", () => {
  submitting = false;
  try {
    sessionStorage.removeItem(positionKey);
  } catch {
  }
});
var pressed = null;
document.addEventListener("pointerdown", (e) => {
  pressed = e.target;
}, true);
document.addEventListener("pointerup", () => setTimeout(() => {
  pressed = null;
}, 0), true);
var positionKey = "screen-position:" + location.pathname;
function savePosition() {
  try {
    const active = document.activeElement;
    sessionStorage.setItem(positionKey, JSON.stringify({
      id: active?.id ?? "",
      name: active?.getAttribute("name") ?? "",
      x: window.scrollX,
      y: window.scrollY,
      time: Date.now()
    }));
  } catch {
  }
}
function restorePosition() {
  try {
    const raw = sessionStorage.getItem(positionKey);
    if (!raw) return;
    sessionStorage.removeItem(positionKey);
    const p = JSON.parse(raw);
    if (Date.now() - p.time > 3e4) return;
    window.scrollTo(p.x, p.y);
    const target = p.id && document.getElementById(p.id) || p.name && document.getElementsByName(p.name)[0] || null;
    if (target instanceof HTMLElement) target.focus({ preventScroll: true });
  } catch {
  }
}
function submitChange(event, row) {
  const form = document.querySelector("form");
  const blocked = () => submitting && !screenUpdate();
  if (!form || blocked()) return;
  setTimeout(() => {
    if (blocked()) return;
    const active = document.activeElement;
    const button = (pressed ?? active)?.closest('button, input[type="submit"], input[type="button"], a');
    if (button && form.contains(button)) {
      const add = (name, value) => {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = name;
        input.value = value;
        input.dataset.transient = "1";
        form.appendChild(input);
      };
      add("_change", event);
      if (row !== void 0) add("_changeRow", String(row));
      savePosition();
      let done = false;
      const finish = () => {
        if (done) return;
        done = true;
        setTimeout(() => {
          if (!submitting) submitEvent(event, row);
        }, 0);
      };
      button.addEventListener("click", finish, { once: true });
      button.addEventListener("blur", finish, { once: true });
      return;
    }
    savePosition();
    submitEvent(event, row);
  }, 0);
}
function onRowClick(grid, handler) {
  document.querySelectorAll(`[data-grid="${grid}"] [data-row]`).forEach((tr) => {
    tr.addEventListener("click", (e) => {
      if (e.target?.closest("button, a, input, select, textarea, label")) return;
      handler(num(tr.dataset.row));
    });
  });
}
var typingDelay = 400;
function valueOf(e) {
  if (e instanceof HTMLInputElement && (e.type === "checkbox" || e.type === "radio")) return String(e.checked);
  return e.value ?? "";
}
var typingStates = /* @__PURE__ */ new WeakMap();
function typing(e, event, fire) {
  let state = typingStates.get(e);
  if (!state) {
    state = { timer: 0, last: e.defaultValue ?? "" };
    if (e instanceof HTMLInputElement && (e.type === "checkbox" || e.type === "radio")) state.last = String(e.defaultChecked);
    if (e instanceof HTMLSelectElement) state.last = [...e.options].find((o) => o.defaultSelected)?.value ?? e.value;
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
  if (event.type === "change") {
    run();
  } else if (!event.isComposing) {
    clearTimeout(s.timer);
    s.timer = window.setTimeout(run, typingDelay);
  }
}
function onTyping(name, handler) {
  const e = element(name);
  if (!e) return;
  for (const type of ["input", "compositionend", "change"]) e.addEventListener(type, (ev) => typing(e, ev, handler));
}

// UserPopup.ts
var UserPopupNodes = {
  n_TableMain: "n:TableMain",
  n_panel1: "n:panel1",
  v_UserCD: "v:UserCD",
  v_UserNm: "v:UserNm",
  v_Users: "v:Users",
  n_SEQ: "n:SEQ",
  v_Users_MST00002_UserCD: "v:Users.MST00002_UserCD",
  v_Users_MST00002_UserNM: "v:Users.MST00002_UserNM",
  v_Users_MST00002_LoginUsr: "v:Users.MST00002_LoginUsr",
  v_Users_MST00002_LoginPwd: "v:Users.MST00002_LoginPwd",
  v_Users_MST00002_MailAddress: "v:Users.MST00002_MailAddress",
  v_Users_MST00002_UserRole: "v:Users.MST00002_UserRole",
  v_Users_MST00002_SectionCD: "v:Users.MST00002_SectionCD",
  v_Users____MST00005_SectionNM: "v:Users.部署.MST00005_SectionNM",
  n_cellPanel1: "n:cellPanel1",
  v_Users_MST00002_CreateUserCD: "v:Users.MST00002_CreateUserCD",
  v_Users_MST00002_UpdateUserCD: "v:Users.MST00002_UpdateUserCD",
  n_btnPanel1: "n:btnPanel1",
  n_btnDetail: "n:btnDetail",
  n_btnEdit: "n:btnEdit",
  n_btnCopy: "n:btnCopy",
  n_btnDelete: "n:btnDelete",
  n_buttonGroup1: "n:buttonGroup1",
  n_btnOk: "n:btnOk",
  n_btnCancel: "n:btnCancel"
};
function bind() {
  onRowClick("Users", (row) => submitEvent("Users_RowClick", row));
  restorePosition();
  onTyping("UserCD", () => submitChange("UserCD_Change"));
  onTyping("UserNm", () => submitChange("UserNm_Change"));
}
bind();
onUpdate(bind);
export {
  UserPopupNodes,
  bind
};
