-- Proposed initial schema for team review. Do not execute before approval.

BEGIN;

CREATE TABLE public.chamados (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    protocolo text NOT NULL DEFAULT gen_random_uuid()::text,
    titulo text NOT NULL,
    descricao text NOT NULL,
    localizacao text NOT NULL,
    foto_path text,
    status text NOT NULL DEFAULT 'RECEBIDO',
    tipo_sugerido text,
    prioridade_sugerida text,
    tipo_revisado text,
    prioridade_revisada text,
    resposta_equipe text,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_chamados_protocolo UNIQUE (protocolo),
    CONSTRAINT chk_chamados_status
        CHECK (status IN ('RECEBIDO', 'EM_ANDAMENTO', 'RESOLVIDO'))
);

CREATE TABLE public.acompanhamento_privado (
    chamado_id uuid PRIMARY KEY
        REFERENCES public.chamados (id),
    email_cifrado text NOT NULL,
    codigo_hash text,
    codigo_expira_em timestamptz,
    tentativas smallint NOT NULL DEFAULT 0,
    confirmado_em timestamptz,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_acompanhamento_tentativas
        CHECK (tentativas >= 0)
);

CREATE INDEX idx_chamados_fila_status_created_at
    ON public.chamados (status, created_at DESC);

CREATE OR REPLACE FUNCTION public.set_chamados_updated_at()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.updated_at := statement_timestamp();
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_chamados_updated_at
BEFORE UPDATE ON public.chamados
FOR EACH ROW
EXECUTE FUNCTION public.set_chamados_updated_at();

COMMENT ON COLUMN public.chamados.protocolo IS
    'Random identifier; it must not authorize complaint lookup by itself.';
COMMENT ON COLUMN public.chamados.foto_path IS
    'Reference to a Supabase Storage object; no bucket or storage policy is created here.';
COMMENT ON COLUMN public.acompanhamento_privado.email_cifrado IS
    'Application-encrypted email only; keep the encryption key outside the database and repository.';
COMMENT ON COLUMN public.acompanhamento_privado.codigo_hash IS
    'Secure representation of the current code only; never store the plaintext code.';

ALTER TABLE public.chamados ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.acompanhamento_privado ENABLE ROW LEVEL SECURITY;

COMMIT;
