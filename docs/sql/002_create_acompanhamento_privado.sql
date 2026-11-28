BEGIN;

CREATE TABLE IF NOT EXISTS public.acompanhamento_privado (
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

ALTER TABLE public.acompanhamento_privado ENABLE ROW LEVEL SECURITY;

COMMIT;
