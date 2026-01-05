import { useState } from "react";
import { api } from "./services/api";

function App() {
  const [url, setUrl] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [error, setError] = useState("");
  const [copied, setCopied] = useState(false);
  const [loading, setLoading] = useState(false);

  function isValidUrl(value: string) {
    try {
      new URL(value);
      return true;
    } catch {
      return false;
    }
  }

  async function handleShorten() {
    setError("");
    setShortUrl("");
    setCopied(false);

    if (!isValidUrl(url)) {
      setError("Digite uma URL válida (ex: https://google.com)");
      return;
    }

    setLoading(true);

    try {
      const response = await api.post("/shorten", { url });
      setShortUrl(response.data.shortUrl);
    } catch {
      setError("Erro ao encurtar a URL");
    } finally {
      setLoading(false);
    }
  }

  function handleCopy() {
    navigator.clipboard.writeText(shortUrl);
    setCopied(true);

    setTimeout(() => setCopied(false), 2000);
  }

  function handleKeyDown(e: React.KeyboardEvent<HTMLInputElement>) {
    if (e.key === "Enter") {
      handleShorten();
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center px-4">
      <div className="bg-zinc-800 w-full max-w-md rounded-xl shadow-xl p-6 space-y-6 animate-fade-in">
        <h1 className="text-2xl font-bold text-center">🔗 URL Shortener</h1>

        <input
          type="text"
          placeholder="https://exemplo.com"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          onKeyDown={handleKeyDown}
          className={`w-full px-4 py-2 rounded-md bg-zinc-900 border
            ${error ? "border-red-500" : "border-zinc-700"}
            focus:outline-none focus:ring-2 focus:ring-indigo-500`}
        />

        <button
          onClick={handleShorten}
          disabled={loading}
          className="w-full bg-indigo-600 hover:bg-indigo-500 transition rounded-md py-2 font-semibold disabled:opacity-50"
        >
          {loading ? "Encurtando..." : "Encurtar"}
        </button>

        {shortUrl && (
          <div className="bg-zinc-900 p-3 rounded-md text-center space-y-2 animate-scale-in">
            <p className="text-sm text-zinc-400">URL curta:</p>

            <a
              href={shortUrl}
              target="_blank"
              rel="noreferrer"
              className="text-indigo-400 hover:underline break-all block"
            >
              {shortUrl}
            </a>

            <button
              onClick={handleCopy}
              className="text-sm bg-zinc-700 hover:bg-zinc-600 px-3 py-1 rounded transition"
            >
              {copied ? "✅ Copiado!" : "📋 Copiar"}
            </button>
          </div>
        )}

        {error && (
          <p className="text-red-400 text-sm text-center animate-shake">
            {error}
          </p>
        )}
      </div>
    </div>
  );
}

export default App;
