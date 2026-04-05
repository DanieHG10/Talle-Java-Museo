# Sistema de Gestión de Obras de Arte (Museo)

Este es el taller POO sobre la gestion de las obras, cuadro y obras del museo desarrollado en Java utilizando Programación Orientada a Objetos.

## Diagrama UML de Clases

A continuación se presenta la arquitectura del sistema:

```mermaid

classDiagram
    %% Enumeraciones
    class EstadoObra {
        <<enumeration>>
        EXPUESTA
        RESTAURACION
    }

    class TipoRestauracion {
        <<enumeration>>
        MANTENIMIENTO
        REPARACION
        RESTAURACION_COMPLETA
    }

    %% Sistema de Usuarios (Herencia)
    class Usuario {
        <<abstract>>
        +nombre: str
        +usuario: str
        -contraseña: str
        +autenticar(usuario, contraseña): bool
        +obtener_menu()*: str
    }

    class EncargadoCatalogo {
        +obtener_menu(): str
    }

    class RestauradorJefe {
        +obtener_menu(): str
    }

    class Director {
        +obtener_menu(): str
    }

    class Visitante {
        +obtener_menu(): str
    }

    Usuario <|-- EncargadoCatalogo
    Usuario <|-- RestauradorJefe
    Usuario <|-- Director
    Usuario <|-- Visitante

    %% Jerarquía de Obras de Arte
    class ObraDeArte {
        <<abstract>>
        +id_obra: str
        +autor: str
        +periodo: str
        +valor: float
        +fecha_creacion: datetime
        +fecha_entrada: datetime
        +estado: EstadoObra
        +obtener_info_especifica()*: str
        +enviar_a_restauracion(): Restauracion
        +necesita_restauracion_automatica(): bool
    }

    class Cuadro {
        +estilo: str
        +tecnica: str
        +obtener_info_especifica(): str
    }

    class Escultura {
        +estilo: str
        +material: str
        +obtener_info_especifica(): str
    }

    class Objeto {
        +descripcion: str
        +obtener_info_especifica(): str
    }

    ObraDeArte <|-- Cuadro
    ObraDeArte <|-- Escultura
    ObraDeArte <|-- Objeto

    %% Relaciones de Negocio
    class Restauracion {
        +id_restauracion: str
        +tipo: TipoRestauracion
        +fecha_inicio: datetime
        +fecha_fin: datetime
    }

    class Cesion {
        +id_cesion: str
        +museo_cesionario: str
        +importe: float
        +fecha_inicio: datetime
        +fecha_fin: datetime
        +esta_vigente(): bool
    }

    %% Composición y Agregación
    ObraDeArte "1" *-- "0..*" Restauracion : historial
    ObraDeArte "1" o-- "0..1" Cesion : cesion_vigente

    %% Gestores Principales
    class GestorMuseo {
        +obras: List~ObraDeArte~
        +restauraciones: List~Restauracion~
        +cesiones: List~Cesion~
        +museos_colaboradores: List~str~
        +registrar_cuadro()
        +iniciar_restauracion()
        +ceder_obra()
        +calcular_valoracion_total(): float
    }

    class SistemaAutenticacion {
        +usuarios: List~Usuario~
        +autenticar(): Usuario
    }

    GestorMuseo o-- ObraDeArte
    GestorMuseo o-- Restauracion
    GestorMuseo o-- Cesion
    SistemaAutenticacion o-- Usuario
```
